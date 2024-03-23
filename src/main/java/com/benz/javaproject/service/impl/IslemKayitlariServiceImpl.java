package com.benz.javaproject.service.impl;


import com.benz.javaproject.entity.IslemKayitlari;
import com.benz.javaproject.entity.KarPayiDagitimi;
import com.benz.javaproject.repository.IslemKayitlarıRepository;
import com.benz.javaproject.service.IslemKayitlariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IslemKayitlariServiceImpl implements IslemKayitlariService {

    private final IslemKayitlarıRepository islemKayitlarıRepository;

    @Autowired
    public IslemKayitlariServiceImpl(IslemKayitlarıRepository islemKayitlarıRepository) {
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
