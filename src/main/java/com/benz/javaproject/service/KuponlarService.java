package com.benz.javaproject.service;

import com.benz.javaproject.entity.HisseSenetleri;
import com.benz.javaproject.entity.Kuponlar;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface KuponlarService {
    public List<Kuponlar> kuponOlustur(HisseSenetleri hisseSenetleri);
    public int findMaxKuponNumarasiBySenetId(Long senetId);
    public List<Kuponlar> searchPayAlmaKuponuByTertipNo(Long tertipNo);
    public Kuponlar save(Kuponlar kupon);
    public List<Kuponlar> findAll(Specification<Kuponlar> spec);
}

