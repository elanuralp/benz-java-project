package com.benz.javaproject.service;

import com.benz.javaproject.entity.SermayeArtisi;
import com.benz.javaproject.model.SermayeArtisSearchModel;
import com.benz.javaproject.repository.SermayeArtisiRepository;
import com.benz.javaproject.specification.SermayeArtisiSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return sermayeArtisiRepository.findById(tertipNo).orElse(null);
    }

    @Transactional
    public SermayeArtisi createSermayeArtisi(SermayeArtisi sermayeArtisi) {
        return sermayeArtisiRepository.save(sermayeArtisi);
    }

    @Transactional
    public SermayeArtisi updateSermayeArtisi(Long tertipNo, SermayeArtisi updatedSermayeArtisi) {
        SermayeArtisi existingSermayeArtisi = sermayeArtisiRepository.findById(tertipNo).orElse(null);

        if (existingSermayeArtisi != null) {
            existingSermayeArtisi.setYil(updatedSermayeArtisi.getYil());
            existingSermayeArtisi.setBedelliArtisMiktari(updatedSermayeArtisi.getBedelliArtisMiktari());
            existingSermayeArtisi.setBedelsizArtisMiktari(updatedSermayeArtisi.getBedelsizArtisMiktari());
            existingSermayeArtisi.setSermayeArtisOrani(updatedSermayeArtisi.getSermayeArtisOrani());
            existingSermayeArtisi.setEskiSermaye(updatedSermayeArtisi.getEskiSermaye());

            return sermayeArtisiRepository.save(existingSermayeArtisi);
        } else {
            return null;
        }
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
        sermayeArtisiRepository.deleteById(tertipNo);
    }
}
