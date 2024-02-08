package com.benz.javaproject.service;

import com.benz.javaproject.entity.*;
import com.benz.javaproject.specification.KarPayiDagitimiSpecification;
import com.benz.javaproject.specification.KuponlarSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.logging.Logger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly=true)
public class IslemlerService {


    private final HisseSenediService hisseSenediService;
    private final HissedarlarService hissedarlarService;
    private final KuponlarService kuponlarService;
    private final KarPayiDagitimiService karPayıDagitimlariService;
    private final SermayeArtisiService sermayeArtisiService;


    @Autowired
    public IslemlerService(HisseSenediService hisseSenediService, HissedarlarService hissedarlarService, KuponlarService kuponlarService,KarPayiDagitimiService karPayıDagitimlariService,SermayeArtisiService sermayeArtisiService) {
        this.hisseSenediService = hisseSenediService;
        this.hissedarlarService = hissedarlarService;
        this.kuponlarService = kuponlarService;
        this.karPayıDagitimlariService = karPayıDagitimlariService;
        this.sermayeArtisiService = sermayeArtisiService;
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
                // Hissedarın sahip olduğu senetleri güncelle (Opsiyonel)
                if (!hissedar.getSenetlerList().contains(alinacakSenet)) {
                    hissedar.getSenetlerList().add(alinacakSenet);
                    hissedarlarService.updateHissedar(hissedar);
                }
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
    private static final Logger logger = Logger.getLogger(IslemlerService.class.getName());




    @Transactional
    public void karPayiDagitimiYap(Long tertipNo, Long hissedarId, int dagitimYili, BigDecimal karPayiOranı) {
        // İstenen sermaye artışını bul
        SermayeArtisi sermayeArtisi = sermayeArtisiService.getSermayeArtisiById(tertipNo);
        if (sermayeArtisi == null) {
            logger.warning("Belirtilen tertip numarasına sahip sermaye artışı bulunamadı.");
            return;
        }

        // İstenen hissedarı bul
        Hissedarlar hissedar = hissedarlarService.getHissedarById(hissedarId);
        if (hissedar == null) {
            logger.warning("Belirtilen hissedar bulunamadı.");
            return;
        }

        // Hisseye sahip olan senetleri al
        List<HisseSenetleri> hisseSenetleri = hisseSenediService.getSenetlerByHissedar(hissedarId);
        if (hisseSenetleri.isEmpty()) {
            logger.warning("Hissedarın sahip olduğu senet bulunamadı.");
            return;
        }

        // Seri numarası bulunarak ilgili sermaye artışına bağlı senetlerin listesi alınır
        List<HisseSenetleri> senetler = hisseSenediService.getSenetlerByTertipNo(tertipNo);
        if (senetler.isEmpty()) {
            logger.warning("Belirtilen sermaye artışına bağlı senet bulunamadı.");
            return;
        }

        // Her bir senet için kar payı dağıtımı yapılır
        for (HisseSenetleri senet : senetler) {
            // Senet hissedara verilmiş mi kontrol et
            if (!hisseSenetleri.contains(senet)) {
                System.out.println("Hissedara ait senet yok");
                continue;
            }

            // Kar payı kuponlarını kontrol et
            List<Kuponlar> karPayiKuponlar = searchKarPayıKuponlarBySenet(senet);

            // Kar payı dağıtımı yapılacak kuponun yılı ile dağıtım yılını kontrol et
            boolean karPayiDagitildi = false;
            for (Kuponlar kupon : karPayiKuponlar) {
                if (kupon.getKuponYili() == dagitimYili && !kupon.isKullanildiMi()) {
                    // Kar payı dağıtımı başarılı olduğunda bir KarPayiDagitimi nesnesi oluştur
                    KarPayiDagitimi karPayiDagitimi = new KarPayiDagitimi();
                    karPayiDagitimi.setSermayeArtisi(sermayeArtisi);
                    karPayiDagitimi.setDagitimYili(dagitimYili);
                    karPayiDagitimi.setSeriNo(senet.getKarPayiDagitimiList().size() + 1); // Senet üzerindeki kar payı dağıtımının kaçıncı olduğunu belirle
                    karPayiDagitimi.setKarPayiOrani(karPayiOranı); // Kar payı tutarını hesapla
                    // Kar payı dağıtımını kaydet
                    karPayıDagitimlariService.saveKarPayiDagitimi(karPayiDagitimi);

                    // Kuponun kullanıldığını işaretle
                    kupon.setKullanildiMi(true);
                    kuponlarService.save(kupon);

                    karPayiDagitildi = true;
                    break;
                }
            }

            // Eğer kar payı dağıtılmadıysa, bir sonraki yılın kuponunu ara
            if (!karPayiDagitildi) {
                dagitimYili++; // Dağıtım yılını bir arttır
            }
        }
    }



    private BigDecimal calculateKarPayiTutari(BigDecimal karPayiOrani, BigDecimal nominalDeger) {
        return karPayiOrani.multiply(nominalDeger);
    }

    public List<Kuponlar> searchKarPayıKuponlarBySenet(HisseSenetleri senet) {
        Specification<Kuponlar> spec = KuponlarSpecification.searchKarPayıKuponlarBySenet(senet);
        return kuponlarService.findAll(spec);
    }











}





