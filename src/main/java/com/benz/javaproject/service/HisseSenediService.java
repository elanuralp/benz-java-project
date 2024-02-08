package com.benz.javaproject.service;

import com.benz.javaproject.entity.HisseSenetleri;
import com.benz.javaproject.entity.Kuponlar;
import com.benz.javaproject.entity.SermayeArtisi;
import com.benz.javaproject.exception.HisseSenediNotExistsError;
import com.benz.javaproject.model.SenetBasRequest;
import com.benz.javaproject.repository.HisseSenetleriRepository;
import com.benz.javaproject.specification.HisseSenetleriSpecification;
import com.benz.javaproject.specification.KuponlarSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class HisseSenediService {

    private final HisseSenetleriRepository hisseSenetleriRepository;
    private final KuponlarService kuponlarService;
    private final SermayeArtisiService sermayeArtisiService;

    private final Map<Long, Integer> lastSeriNoMap = new HashMap<>();

    @Autowired
    public HisseSenediService(HisseSenetleriRepository hisseSenetleriRepository, KuponlarService kuponlarService, SermayeArtisiService sermayeArtisiService) {
        this.hisseSenetleriRepository = hisseSenetleriRepository;
        this.kuponlarService = kuponlarService;
        this.sermayeArtisiService = sermayeArtisiService;
    }

    public List<HisseSenetleri> getAllHisseSenetleri() {
        return hisseSenetleriRepository.findAll();
    }

    public HisseSenetleri getHisseSenetleriBySeriNo(int seriNo) {
        return hisseSenetleriRepository.findOne(HisseSenetleriSpecification.bySeriNo(seriNo))
                .orElseThrow(HisseSenediNotExistsError::new);
    }


    public List<HisseSenetleri> getSenetlerByTertipNo(Long tertipNo) {
        // Belirtilen tertip numarasına sahip sermaye artışına bağlı senetleri al
        Specification<HisseSenetleri> spec = HisseSenetleriSpecification.searchByTertipNo(tertipNo);
        return hisseSenetleriRepository.findAll(spec);
    }

    public List<HisseSenetleri> getSenetlerByHissedar(Long hissedarId) {
        return hisseSenetleriRepository.findAll(HisseSenetleriSpecification.searchByHissedarId(hissedarId));
    }



    @Transactional
    public List<HisseSenetleri> olusturSenetler(List<SenetBasRequest> senetListesi, Long tertipNo) {
        SermayeArtisi sermayeArtisi = sermayeArtisiService.getSermayeArtisiById(tertipNo);
        Integer lastSeriNo = findMaxSeriNoByTertipNo(tertipNo);
        if (lastSeriNo == null) {
            lastSeriNo = 0;
        }
        // Nominal değere göre senet listesini küçükten büyüğe sırala
        senetListesi.sort(Comparator.comparing(SenetBasRequest::getNominal));
        List<HisseSenetleri> yeniSenetler = new ArrayList<>();
        for (SenetBasRequest senet : senetListesi) {
            for (int i = 0; i < senet.getAdet(); i++) {
                HisseSenetleri yeniSenet = new HisseSenetleri();
                yeniSenet.setNominalDeger(senet.getNominal());
                yeniSenet.setSermayeArtisi(sermayeArtisi);
                yeniSenet.setSeriNo(++lastSeriNo);
                yeniSenetler.add(yeniSenet);
            }
        }
        List<HisseSenetleri> kaydedilenSenetler = hisseSenetleriRepository.saveAll(yeniSenetler);
        for (HisseSenetleri senet : kaydedilenSenetler) {
            List<Kuponlar> olusturulanKuponlar = kuponlarService.kuponOlustur(senet);
            senet.setKuponlarList(olusturulanKuponlar);
        }
        return kaydedilenSenetler;
    }


    public HisseSenetleri getAlinabilirSenetBySeriNo(Long seriNo) {
        // Belirtilen seri numarasına sahip senedi bul
        Specification<HisseSenetleri> spec = KuponlarSpecification.searchBySeriNo(seriNo);
        List<HisseSenetleri> senetler = hisseSenetleriRepository.findAll(spec);
        if (senetler.isEmpty()) {
            return null;
        } else {
            return senetler.get(0); // Örneğin, ilk seneti seçtik
        }
    }





    @Transactional
    public HisseSenetleri save(HisseSenetleri senet) {
        return hisseSenetleriRepository.save(senet);
    }




    Integer findMaxSeriNoByTertipNo(Long tertipNo) {
        return hisseSenetleriRepository.findMaxSeriNoByTertipNo(tertipNo);
    }


    public List<HisseSenetleri> findBySpec(Specification<HisseSenetleri> spec) {
        return hisseSenetleriRepository.findAll(spec);
    }







//    public List<HisseSenetleri> searchHisseSenetleri(HisseSenediSearchModel hisseSenediSearchModel) {
//        Specification<HisseSenetleri> specification = HisseSenetleriSpecification.buildSpecification(hisseSenediSearchModel);
//        return hisseSenetleriRepository.findAll(specification);
//    }

    @Transactional
    public void deleteHisseSenediBySeriNo(int seriNo) {

        HisseSenetleri existingHisseSenedi = hisseSenetleriRepository.findOne(HisseSenetleriSpecification.bySeriNo(seriNo))
                .orElseThrow(() -> new HisseSenediNotExistsError());
        hisseSenetleriRepository.delete(existingHisseSenedi);
    }
}
