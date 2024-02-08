package com.benz.javaproject.service;

import com.benz.javaproject.entity.*;
import com.benz.javaproject.exception.HissedarNotExistsError;
import com.benz.javaproject.specification.KuponlarSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly=true)
public class IslemlerService {


    private final HisseSenediService hisseSenediService;
    private final HissedarlarService hissedarlarService;
    private final KuponlarService kuponlarService;


    @Autowired
    public IslemlerService(HisseSenediService hisseSenediService, HissedarlarService hissedarlarService, KuponlarService kuponlarService) {
        this.hisseSenediService = hisseSenediService;
        this.hissedarlarService = hissedarlarService;
        this.kuponlarService = kuponlarService;
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


    public List<Kuponlar> searchKullanilmamisKuponlarBySenet(HisseSenetleri senet) {
        // Belirtilen senete ait kullanılmamış kuponları al
        Specification<Kuponlar> spec = KuponlarSpecification.searchKullanilmamisPayAlmaKuponlarBySenet(senet);
        return kuponlarService.findAll(spec);
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

}





