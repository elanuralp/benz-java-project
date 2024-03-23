package com.benz.javaproject.service;

import com.benz.javaproject.entity.KarPayiDagitimi;
import com.benz.javaproject.repository.KarPayıDagıtımıRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface KarPayiDagitimiService {
    public List<KarPayiDagitimi> getAllKarPayiDagitimi();
    public KarPayiDagitimi getKarPayiDagitimiById(Long id);
    public KarPayiDagitimi saveKarPayiDagitimi(KarPayiDagitimi karPayiDagitimi);
    public void deleteKarPayiDagitimi(Long id);

}
