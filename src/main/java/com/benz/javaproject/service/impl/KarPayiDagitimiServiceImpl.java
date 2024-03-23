package com.benz.javaproject.service.impl;


import com.benz.javaproject.entity.KarPayiDagitimi;
import com.benz.javaproject.repository.KarPayıDagıtımıRepository;
import com.benz.javaproject.service.KarPayiDagitimiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KarPayiDagitimiServiceImpl implements KarPayiDagitimiService {

    private final KarPayıDagıtımıRepository karPayiDagitimiRepository;

    @Autowired
    public KarPayiDagitimiServiceImpl(KarPayıDagıtımıRepository karPayiDagitimiRepository) {
        this.karPayiDagitimiRepository = karPayiDagitimiRepository;
    }

    public List<KarPayiDagitimi> getAllKarPayiDagitimi() {
        return karPayiDagitimiRepository.findAll();
    }

    public KarPayiDagitimi getKarPayiDagitimiById(Long id) {
        return karPayiDagitimiRepository.findById(id).orElse(null);
    }

    public KarPayiDagitimi saveKarPayiDagitimi(KarPayiDagitimi karPayiDagitimi) {
        return karPayiDagitimiRepository.save(karPayiDagitimi);
    }

    public void deleteKarPayiDagitimi(Long id) {
        karPayiDagitimiRepository.deleteById(id);
    }
}
