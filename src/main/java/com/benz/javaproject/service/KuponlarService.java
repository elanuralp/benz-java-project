package com.benz.javaproject.service;

import com.benz.javaproject.entity.HisseSenetleri;
import com.benz.javaproject.entity.Kuponlar;
import com.benz.javaproject.enums.KuponTuru;
import com.benz.javaproject.repository.KuponlarRepository;
import com.benz.javaproject.specification.KarPayiDagitimiSpecification;
import com.benz.javaproject.specification.KuponlarSpecification;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly=true)
public class KuponlarService {

    private final KuponlarRepository kuponlarRepository;

    @Autowired
    public KuponlarService(KuponlarRepository kuponlarRepository){
        this.kuponlarRepository = kuponlarRepository;
    }

    @Transactional
    public List<Kuponlar> kuponOlustur(HisseSenetleri hisseSenetleri) {
        int toplamKuponSayisi = 26; // Toplam kupon sayısı
        int payAlmaKuponSayisi = 16; // Pay alma kuponu sayısı
        List<Kuponlar> kuponlarList = new ArrayList<>();
        // Sermaye artışının yapıldığı yıl
        Integer sermayeArtisiYili = hisseSenetleri.getSermayeArtisi().getYil();
        // Her bir senet için kupon oluştur
        for (int i = 1; i <= toplamKuponSayisi; i++) {
            Kuponlar kupon = new Kuponlar();
            kupon.setKuponTuru((i <= payAlmaKuponSayisi) ? KuponTuru.PAY_ALMA : KuponTuru.KAR_PAYI);
            if (kupon.getKuponTuru() == KuponTuru.KAR_PAYI) {
                //System.out.println(i);
                kupon.setKuponYili(sermayeArtisiYili + i - payAlmaKuponSayisi - 1);
            } else {
                kupon.setKuponYili(null); // Pay alma kuponlarında yıl değeri null olmalı
            }
            kupon.setKupurNo((i <= payAlmaKuponSayisi) ? i : null); // Küpür numarası sadece pay alma
            kupon.setKullanildiMi(false);
            kupon.setSenet(hisseSenetleri); // Kaydedilmiş HisseSenetleri nesnesine atıfta bulun
            // Her bir senet için en son kupon numarasını bul
            int lastKuponNo = findMaxKuponNumarasiBySenetId(hisseSenetleri.getSenetId());
            kupon.setKuponNumarasi(lastKuponNo + i); // Her bir kupon numarası, son kupon numarasından başlayarak artacak
            kuponlarList.add(kupon);
        }
        return kuponlarRepository.saveAll(kuponlarList);
    }


    public int findMaxKuponNumarasiBySenetId(Long senetId) {
        Integer maxKuponNumarasi = kuponlarRepository.findMaxKuponNumarasiBySenetId(senetId);
        return maxKuponNumarasi != null ? maxKuponNumarasi : 0;
    }


    public List<Kuponlar> searchPayAlmaKuponuByTertipNo(Long tertipNo) {
        return kuponlarRepository.findAll(KuponlarSpecification.searchPayAlmaKuponuByTertipNo(tertipNo));
    }

    @Transactional
    public Kuponlar save(Kuponlar kupon) {
        return kuponlarRepository.save(kupon);
    }

    public List<Kuponlar> searchEnKucukKupurNoluPayAlmaKuponuListBySeriNo(Long seriNo) {
        Specification<Kuponlar> specification = KuponlarSpecification.searchEnKucukKupurNoluPayAlmaKuponuListBySeriNo(seriNo);
        List<Kuponlar> kuponlar = kuponlarRepository.findAll(specification);
        return kuponlar;
    }


    public Kuponlar getUygunKarPayiKuponu(HisseSenetleri senet, int dagitimYili) {
        Specification<Kuponlar> spec = KarPayiDagitimiSpecification.getUygunKarPayiKuponuSpec(senet, dagitimYili);
        return kuponlarRepository.findOne(spec).orElse(null);
    }




//    public List<Kuponlar> getHisseSenedineBagliKarPayiKuponlari(Long senetId) {
//
//      List<Kuponlar> karPayiKuponlar = kuponlarRepository.findAll(KuponlarSpecification.kuponlarHisseSenedineBagli(senetId));
//      return karPayiKuponlar;
//    }







    public List<Kuponlar> findAll(Specification<Kuponlar> spec) {
        return kuponlarRepository.findAll(spec);
    }


}
