package com.benz.javaproject.service;

import com.benz.javaproject.entity.IslemKayitlari;
import com.benz.javaproject.entity.KarPayiDagitimi;
import com.benz.javaproject.repository.IslemKayitlarıRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IslemKayitlariService {
    public List<IslemKayitlari> getAllIslemKayitlari();
    public IslemKayitlari saveIslemKaydi(IslemKayitlari islemKayitlari);
    public void deleteIslemKaydi(Long id);
}