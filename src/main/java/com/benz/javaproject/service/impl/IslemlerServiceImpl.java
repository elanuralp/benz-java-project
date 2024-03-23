package com.benz.javaproject.service.impl;


import com.benz.javaproject.entity.*;
import com.benz.javaproject.enums.IslemTipi;
import com.benz.javaproject.service.*;
import com.benz.javaproject.specification.KarPayiDagitimiSpecification;
import com.benz.javaproject.specification.KuponlarSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly=true)
public class IslemlerServiceImpl implements IslemlerService {


    private final HisseSenediService hisseSenediService;
    private final HissedarlarService hissedarlarService;
    private final KuponlarService kuponlarService;
    private final KarPayiDagitimiService karPayıDagitimlariService;
    private final SermayeArtisiService sermayeArtisiService;

    private final IslemKayitlariService islemKayitlariService;

    @Autowired
    public IslemlerServiceImpl(HisseSenediService hisseSenediService, HissedarlarService hissedarlarService, KuponlarService kuponlarService,KarPayiDagitimiService karPayıDagitimlariService,SermayeArtisiService sermayeArtisiService,IslemKayitlariService islemKayitlariService) {
        this.hisseSenediService = hisseSenediService;
        this.hissedarlarService = hissedarlarService;
        this.kuponlarService = kuponlarService;
        this.karPayıDagitimlariService = karPayıDagitimlariService;
        this.sermayeArtisiService = sermayeArtisiService;
        this.islemKayitlariService = islemKayitlariService;
    }



    @Transactional
    public void senetVer(Long hissedarId, Long seriNo) {
        // Hissedarı bul
        Hissedarlar hissedar = hissedarlarService.getHissedarById(hissedarId);
        // Belirtilen seri numarasına sahip seneti al
        HisseSenetleri alinacakSenet = getAlinabilirSenetBySeriNo(seriNo);
        if (alinacakSenet != null) {
            // Seri numarasına sahip senet bulundu
            // Kullanılmamış kuponları bul
            List<Kuponlar> kullanilmamisKuponlar = searchKullanilmamisKuponlarBySenet(alinacakSenet);
            if (!kullanilmamisKuponlar.isEmpty()) {
                Kuponlar enKucukKupurNoluKupon = kullanilmamisKuponlar.get(0); // En küçük küpür numaralı kullanılmamış kuponu seç
                // Kupon kullanılmamışsa seneti hissedara ver
                enKucukKupurNoluKupon.setKullanildiMi(true);
                kuponlarService.save(enKucukKupurNoluKupon);
                // Senetin hissedarını güncelle
                alinacakSenet.setHissedar(hissedar);
                alinacakSenet.getKuponlarList().add(enKucukKupurNoluKupon); // Senet üzerindeki kupon listesine ekleyelim
                hisseSenediService.save(alinacakSenet);
                // Hissedarın sahip olduğu senetleri güncelle
                if (!hissedar.getSenetlerList().contains(alinacakSenet)) {
                    hissedar.getSenetlerList().add(alinacakSenet);
                    hissedarlarService.updateHissedar(hissedar);
                }
                // İşlem kaydını oluştur
                IslemKayitlari islemKayitlari = new IslemKayitlari();
                islemKayitlari.setIslemTipi(IslemTipi.HISSE_SENETI); // İşlem tipi kar payı dağıtımı olarak ayarlanır
                islemKayitlari.setKarPayiDagitimi(null); // İlgili kar payı dağıtımı
                islemKayitlari.setIslemZamani(LocalDateTime.now());
                islemKayitlari.setKarPayiTutari(null);
                islemKayitlari.setHisseSenetleri(alinacakSenet); // İlgili senet
                islemKayitlari.setSeriNo(null); // Seri numarası

                // İşlem kaydını veritabanına kaydet
                islemKayitlariService.saveIslemKaydi(islemKayitlari);

            } else {
                // Kullanılabilecek kupon bulunamadı
                System.out.println("Belirtilen seri numarasına sahip senet için kullanılabilecek kupon bulunamadı.");
            }
        } else {
            // Belirtilen seri numarasına sahip senet bulunamadı
            System.out.println("Belirtilen seri numarasına sahip senet bulunamadı.");
        }
    }




    public HisseSenetleri getAlinabilirSenetBySeriNo(Long seriNo) {
        // Belirtilen seri numarasına sahip
        Specification<HisseSenetleri> spec = KuponlarSpecification.searchBySeriNo(seriNo);
        List<HisseSenetleri> senetler = hisseSenediService.findBySpec(spec);
        if (senetler.isEmpty()) {
            return null;
        } else {
            return senetler.get(0); // Örneğin, ilk seneti seçtik
        }
    }

    public List<Kuponlar> searchKullanilmamisKuponlarBySenet(HisseSenetleri senet) {
        // Belirtilen senete ait kullanılmamış kuponları al
        Specification<Kuponlar> spec = KuponlarSpecification.searchKullanilmamisPayAlmaKuponlarBySenet(senet);
        return kuponlarService.findAll(spec);
    }
    // Logger nesnesini tanımla




    @Transactional
    public void karPayiDagitimiYap(Long tertipNo, int dagitimYili, BigDecimal karPayiOrani) {
        // İstenen sermaye artışını bul
        SermayeArtisi sermayeArtisi = sermayeArtisiService.getSermayeArtisiById(tertipNo);
        if (sermayeArtisi == null) {
            //hata tanımı
            return;
        }
        // Seri numarası bulunarak ilgili sermaye artışına bağlı senetlerin listesi alınır
        List<HisseSenetleri> senetler = hisseSenediService.getSenetlerByTertipNo(tertipNo);
        if (senetler.isEmpty()) {
            //hata tanımı
            return;
        }
        // Her bir senet için kar payı dağıtımı yapılır
        for (HisseSenetleri senet : senetler) {
            // Hissedara atanmış senetleri al
            if (senet.getHissedar() == null) {
                System.out.println("Hissedara atanmış senet yok");
                continue;
            }
            // Kar payı kuponlarını kontrol et
            List<Kuponlar> karPayiKuponlar = searchKarPayıKuponlarBySenet(senet);
            int currentDagitimYili = dagitimYili; // Dağıtım yılını güncellemek için bir kopya oluştur
            int seriNo = 1;
            // Uygun kupon bulunana kadar devam et
            boolean uygunKuponBulundu = false;
            int kuponSayisi = 10;
            while (!uygunKuponBulundu && kuponSayisi <10) {
                for (Kuponlar kupon : karPayiKuponlar) {
                    if (kupon.getKuponYili() == currentDagitimYili && !kupon.isKullanildiMi()) {
                        // Kar payı dağıtımı başarılı olduğunda bir KarPayiDagitimi nesnesi oluştur
                        KarPayiDagitimi karPayiDagitimi = new KarPayiDagitimi();
                        karPayiDagitimi.setSermayeArtisi(sermayeArtisi);
                        karPayiDagitimi.setDagitimYili(dagitimYili);
                        karPayiDagitimi.setKarPayiOrani(karPayiOrani); // Kar payı tutarını hesapla
                        karPayiDagitimi.setSeriNo(seriNo);
                        // Kar payı dağıtımını kaydet
                        karPayıDagitimlariService.saveKarPayiDagitimi(karPayiDagitimi);
                        // Senetin kar payı dağıtımları listesine ekle
                        senet.getKarPayiDagitimiList().add(karPayiDagitimi);
                        // Kuponun kullanıldığını işaretle
                        kupon.setKullanildiMi(true);
                        kuponlarService.save(kupon);
                        // İşlem kaydını oluştur
                        IslemKayitlari islemKayitlari = new IslemKayitlari();
                        islemKayitlari.setIslemTipi(IslemTipi.KAR_PAYI); // İşlem tipi kar payı dağıtımı olarak ayarlanır
                        islemKayitlari.setKarPayiDagitimi(karPayiDagitimi); // İlgili kar payı dağıtımı
                        islemKayitlari.setIslemZamani(LocalDateTime.now());
                        BigDecimal karPayiTutari = senet.getNominalDeger().multiply(karPayiOrani); // Kar payı tutarı = nominal değer x kar payı oranı
                        islemKayitlari.setKarPayiTutari(karPayiTutari);
                        islemKayitlari.setHisseSenetleri(senet); // İlgili senet
                        islemKayitlari.setSeriNo(karPayiDagitimi.getSeriNo()); // Seri numarası
                        // İşlem kaydını veritabanına kaydet
                        islemKayitlariService.saveIslemKaydi(islemKayitlari);

                        uygunKuponBulundu = true;
                        break;
                    }
                }
                // Eğer uygun kupon bulunamazsa, dağıtım yılını bir sonraki yıla taşı
                if (!uygunKuponBulundu) {
                    currentDagitimYili++;
                    seriNo=1; //yeni yılın ilk dağıtımı
                }else {
                    seriNo++;
                }
                kuponSayisi++;
            }
            if (!uygunKuponBulundu) {
                System.out.println("Uygun kar payı kuponu bulunamadı.");
            }
        }
    }



    public List<Kuponlar> searchKarPayıKuponlarBySenet(HisseSenetleri senet) {
        Specification<Kuponlar> spec = KuponlarSpecification.searchKarPayıKuponlarBySenet(senet);
        return kuponlarService.findAll(spec);
    }











}





