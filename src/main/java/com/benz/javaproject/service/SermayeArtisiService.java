package com.benz.javaproject.service;

import com.benz.javaproject.entity.SermayeArtisi;
import com.benz.javaproject.repository.SermayeArtisiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SermayeArtisiService
{
    private final SermayeArtisiRepository sermayeArtisiRepository;

    @Autowired
    public SermayeArtisiService(SermayeArtisiRepository sermayeArtisiRepository) {
        this.sermayeArtisiRepository = sermayeArtisiRepository;
    }

    public Optional<SermayeArtisi> getSermayeArtisiById(Long id) {
        return sermayeArtisiRepository.findById(id);
    }


    public List<SermayeArtisi> getAllSermayeArtisi(){
        return sermayeArtisiRepository.findAll();
    }

    public SermayeArtisi saveSermayeArtisi(SermayeArtisi sermayeArtisi){
        return sermayeArtisiRepository.save(sermayeArtisi);
    }

    public void deleteSermayeArtisiById(Long id){
        sermayeArtisiRepository.deleteById(id);
    }



    public SermayeArtisi createSermayeArtisi(double bedelliArtisMiktari, double bedelsizArtisMiktari, double sermayeArtisOrani, double eskiSermaye) {
        SermayeArtisi yeniSermayeArtisi = new SermayeArtisi();

        // Bedelli ve bedelsiz artışları topla
        double toplamArtis = bedelliArtisMiktari + bedelsizArtisMiktari;

        // Yeni sermayeyi hesapla: Eski sermaye + Toplam artış
        double yeniSermaye = eskiSermaye + toplamArtis;


        return sermayeArtisiRepository.save(yeniSermayeArtisi);
    }
}
