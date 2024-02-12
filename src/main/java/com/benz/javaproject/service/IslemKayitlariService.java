package com.benz.javaproject.service;

import com.benz.javaproject.entity.IslemKayitlari;
import com.benz.javaproject.entity.KarPayiDagitimi;
import com.benz.javaproject.repository.IslemKayitlarıRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IslemKayitlariService {

    private final IslemKayitlarıRepository islemKayitlarıRepository;

    @Autowired
    public IslemKayitlariService(IslemKayitlarıRepository islemKayitlarıRepository) {
        this.islemKayitlarıRepository = islemKayitlarıRepository;
    }

    public List<IslemKayitlari> getAllIslemKayitlari() {
        return islemKayitlarıRepository.findAll();
    }


    public IslemKayitlari saveIslemKaydi(IslemKayitlari islemKayitlari) {
        return islemKayitlarıRepository.save(islemKayitlari);
    }

    public void deleteIslemKaydi(Long id) {
        islemKayitlarıRepository.deleteById(id);
    }
}
