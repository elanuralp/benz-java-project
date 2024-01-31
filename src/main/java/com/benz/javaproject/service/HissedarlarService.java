package com.benz.javaproject.service;

import com.benz.javaproject.entity.HisseSenetleri;
import com.benz.javaproject.entity.Hissedarlar;
import com.benz.javaproject.exception.HissedarNotExistsError;
import com.benz.javaproject.exception.SenetBulunamadiError;
import com.benz.javaproject.exception.SicilNoExistsError;
import com.benz.javaproject.exception.SicilNoNotValidError;
import com.benz.javaproject.model.hissedar.HissedarSearchModel;
import com.benz.javaproject.repository.HissedarlarRepository;
import com.benz.javaproject.specification.HissedarlarSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
public class HissedarlarService {

    private final HissedarlarRepository hissedarlarRepository;
    private final HissedarlarSpecification hissedarlarSpecification;

    @Autowired
    public HissedarlarService(HissedarlarRepository hissedarlarRepository,HissedarlarSpecification hissedarlarSpecification) {
        this.hissedarlarRepository = hissedarlarRepository;
        this.hissedarlarSpecification = hissedarlarSpecification;
    }


    public List<Hissedarlar> getAllHissedarlar() {
        return hissedarlarRepository.findAll();
    }

    public Hissedarlar getHissedarById(Long id) {
        try {
            Hissedarlar hissedar = hissedarlarRepository.findById(id)
                    .orElseThrow(HissedarNotExistsError::new);

            // Eğer hissedar varsa, sicil numarasını kontrol et
            if (!isSicilNumarasiUnique(hissedar.getSicilNumarasi())) {
                throw new SicilNoExistsError();
            }
            return hissedar;
        } catch (SicilNoExistsError e) {
            // Sicil numarası zaten mevcut hatası
            throw e;
        }
    }


    @Transactional
    public Hissedarlar createHissedar(Hissedarlar hissedar) {
        if (isSicilNumarasiUnique(hissedar.getSicilNumarasi()) && isSicilNumarasiValid(hissedar.getSicilNumarasi())) {
            return hissedarlarRepository.save(hissedar);
        } else {
            // siciexists
            throw new SicilNoExistsError();
        }
    }

    @Transactional
    public Hissedarlar updateHissedar(Long id, Hissedarlar updatedHissedar) {
        Hissedarlar existingHissedar = hissedarlarRepository.findById(id).orElse(null);


        if (existingHissedar != null) {
            // Güncelleme işlemleri burada yapılır
            existingHissedar.setUnvan(updatedHissedar.getUnvan());
            existingHissedar.setAdres(updatedHissedar.getAdres());
            existingHissedar.setTelefon(updatedHissedar.getTelefon());
            existingHissedar.setYatirimciTipi(updatedHissedar.getYatirimciTipi());
            existingHissedar.setSicilNumarasi(updatedHissedar.getSicilNumarasi());

            isSicilNumarasiValid(existingHissedar.getSicilNumarasi());

            return hissedarlarRepository.save(existingHissedar);
        } else {
            throw new HissedarNotExistsError();
        }
    }

    @Transactional
    public List<Hissedarlar> addMultipleHissedarlar(List<Hissedarlar> hissedarList) {

        //check sicil
        for (Hissedarlar hissedar : hissedarList) {
            if (!isSicilNumarasiUnique(hissedar.getSicilNumarasi())) {
                throw new SicilNoExistsError() ;
            }
        }
        hissedarList.forEach(h -> isSicilNumarasiValid(h.getSicilNumarasi()));
        return hissedarlarRepository.saveAll(hissedarList);
    }


    public boolean isSicilNumarasiUnique(String sicilNumarasi) {
        if (!hissedarlarRepository.findBySicilNumarasi(sicilNumarasi).isEmpty()) {
            throw new SicilNoExistsError();
        }
        return true;
    }

    private boolean isSicilNumarasiValid(String sicilNumarasi) {
        if (sicilNumarasi.startsWith("0") || sicilNumarasi.length()!=8) {
            throw new SicilNoNotValidError();
        }
        return true;
    }



    //arama
    public List<Hissedarlar> searchHissedarlar(HissedarSearchModel hissedarlarSearchModel) {

        Specification<Hissedarlar> specification = HissedarlarSpecification.searchHissedarlarBySearchModel(hissedarlarSearchModel);
        if (specification == null){
            throw new HissedarNotExistsError();
        }
        return hissedarlarRepository.findAll(specification);



    }

    @Transactional
    public void deleteHissedar(Long id) {
        Hissedarlar existingHissedar = hissedarlarRepository.findById(id)
                .orElseThrow(HissedarNotExistsError::new);
        hissedarlarRepository.deleteById(id);
    }



    //hissedarın senetleri
    public List<HisseSenetleri> getHissedarSenetleri(Long hissedarId) {
        Hissedarlar hissedar = hissedarlarRepository.findById(hissedarId)
                .orElseThrow(SenetBulunamadiError::new);

        return hissedar.getSenetlerList();
    }
}
