package com.benz.javaproject.service;

import com.benz.javaproject.entity.SermayeArtisi;
import com.benz.javaproject.exception.SermayeArtisiNotFoundError;
import com.benz.javaproject.model.SermayeArtisi.SermayeArtisSearchModel;
import com.benz.javaproject.model.SermayeArtisi.SermayeArtisiUpdateModel;
import com.benz.javaproject.model.SermayeArtisi.SermayeArtısıAddModel;
import com.benz.javaproject.repository.SermayeArtisiRepository;
import com.benz.javaproject.specification.SermayeArtisiSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class SermayeArtisiService {

    private final SermayeArtisiRepository sermayeArtisiRepository;

    @Autowired
    public SermayeArtisiService(SermayeArtisiRepository sermayeArtisiRepository) {
        this.sermayeArtisiRepository = sermayeArtisiRepository;
    }

    public List<SermayeArtisi> getAllSermayeArtisi() {
        return sermayeArtisiRepository.findAll();
    }

    public SermayeArtisi getSermayeArtisiById(Long tertipNo) {
        return sermayeArtisiRepository.findById(tertipNo).orElseThrow(SermayeArtisiNotFoundError::new);
    }

    @Transactional
    public SermayeArtisi createSermayeArtisi(SermayeArtısıAddModel sermayeArtisiAddModel) {
        SermayeArtisi sermayeArtisi = new SermayeArtisi();
        sermayeArtisi.setBedelliArtisMiktari(sermayeArtisiAddModel.getBedelliArtisMiktari());
        sermayeArtisi.setYil(sermayeArtisiAddModel.getYıl());
        sermayeArtisi.setBedelsizArtisMiktari(sermayeArtisiAddModel.getBedelsizArtisMiktari());
        sermayeArtisi.setSermayeArtisOrani(sermayeArtisiAddModel.getSermayeArtisOrani());
        sermayeArtisi.setEskiSermaye(sermayeArtisiAddModel.getEskiSermaye());
        updateOldCapital(sermayeArtisi);
        return sermayeArtisiRepository.save(sermayeArtisi);
    }

    @Transactional
    public SermayeArtisi updateSermayeArtisi(Long tertipNo, SermayeArtisiUpdateModel updatedSermayeArtisiAddModel) {
        SermayeArtisi existingSermayeArtisi = sermayeArtisiRepository.findById(tertipNo).orElseThrow(SermayeArtisiNotFoundError::new);
        existingSermayeArtisi.setYil(updatedSermayeArtisiAddModel.getYıl());
        existingSermayeArtisi.setBedelliArtisMiktari(updatedSermayeArtisiAddModel.getBedelliArtisMiktari());
        existingSermayeArtisi.setBedelsizArtisMiktari(updatedSermayeArtisiAddModel.getBedelsizArtisMiktari());
        existingSermayeArtisi.setSermayeArtisOrani(updatedSermayeArtisiAddModel.getSermayeArtisOrani());
        existingSermayeArtisi.setEskiSermaye(updatedSermayeArtisiAddModel.getEskiSermaye());
        // Diğer güncelleme işlemleri
        return sermayeArtisiRepository.save(existingSermayeArtisi);
    }


    @Transactional
    public List<SermayeArtisi> addMultipleSermayeArtisi(List<SermayeArtisi> sermayeArtisiList) {
        return sermayeArtisiRepository.saveAll(sermayeArtisiList);
    }

    public List<SermayeArtisi> searchSermayeArtisi(SermayeArtisSearchModel searchModel) {
        Specification<SermayeArtisi> specification = SermayeArtisiSpecification.searchSermayeArtisiBySearchModel(searchModel);
        return sermayeArtisiRepository.findAll(specification);
    }

    @Transactional
    public void deleteSermayeArtisi(Long tertipNo) {
        SermayeArtisi existingSermayeArtisi = sermayeArtisiRepository.findById(tertipNo)
                .orElseThrow(SermayeArtisiNotFoundError::new);
        sermayeArtisiRepository.deleteById(tertipNo);
    }




    @Transactional
    public void updateOldCapital(SermayeArtisi sermayeArtisi) {
        if (sermayeArtisi != null) {
            BigDecimal oldCapital = sermayeArtisi.getEskiSermaye();
            BigDecimal newCapital = oldCapital.add(sermayeArtisi.getBedelliArtisMiktari()).add(sermayeArtisi.getBedelsizArtisMiktari());
            sermayeArtisi.setEskiSermaye(newCapital);
            sermayeArtisiRepository.save(sermayeArtisi);
        } else {
            throw new SermayeArtisiNotFoundError();
        }
    }


}
