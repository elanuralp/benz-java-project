package com.benz.javaproject.service;

import com.benz.javaproject.entity.HisseSenetleri;
import com.benz.javaproject.entity.Hissedarlar;
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
        return hissedarlarRepository.findById(id).orElse(null);
    }

    @Transactional
    public Hissedarlar createHissedar(Hissedarlar hissedar) {
        if (isSicilNumarasiUnique(hissedar.getSicilNumarasi())) {
            return hissedarlarRepository.save(hissedar);
        } else {
            // siciexists
            return null;
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

            return hissedarlarRepository.save(existingHissedar);
        } else {
            return null;
        }
    }

    @Transactional
    public List<Hissedarlar> addMultipleHissedarlar(List<Hissedarlar> hissedarList) {

        //check sicil
        for (Hissedarlar hissedar : hissedarList) {
            if (!isSicilNumarasiUnique(hissedar.getSicilNumarasi())) {
                throw new IllegalArgumentException("Aynı sicil numarasına sahip bir hissedar zaten mevcut: " + hissedar.getSicilNumarasi());
            }
        }
        return hissedarlarRepository.saveAll(hissedarList);
    }


    public boolean isSicilNumarasiUnique(String sicilNumarasi) {
        return hissedarlarRepository.findBySicilNumarasi(sicilNumarasi).isEmpty();
    }


    //arama
    public List<Hissedarlar> searchHissedarlar(HissedarSearchModel hissedarlarSearchModel) {
        Specification<Hissedarlar> specification = HissedarlarSpecification.searchHissedarlarBySearchModel(hissedarlarSearchModel);
        return hissedarlarRepository.findAll(specification);
    }

    @Transactional
    public void deleteHissedar(Long id) {
        hissedarlarRepository.deleteById(id);
    }


    //hissedarın senetleri
    public List<HisseSenetleri> getHissedarSenetleri(Long hissedarId) {
        Hissedarlar hissedar = hissedarlarRepository.findById(hissedarId)
                .orElseThrow(() -> new EntityNotFoundException("Hissedar bulunamadı with id: " + hissedarId));

        return hissedar.getSenetlerList();
    }


}
