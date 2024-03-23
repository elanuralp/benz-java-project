package com.benz.javaproject.service;

import com.benz.javaproject.entity.HisseSenetleri;
import com.benz.javaproject.entity.Kuponlar;
import com.benz.javaproject.entity.SermayeArtisi;
import com.benz.javaproject.exception.HisseSenediNotExistsError;
import com.benz.javaproject.model.hissesenedi.SenetBasRequest;
import com.benz.javaproject.repository.HisseSenetleriRepository;
import com.benz.javaproject.specification.HisseSenetleriSpecification;
import com.benz.javaproject.specification.KuponlarSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface HisseSenediService {
    public List<HisseSenetleri> getAllHisseSenetleri();
    public HisseSenetleri getHisseSenetleriBySeriNo(int seriNo);
    public List<HisseSenetleri> getSenetlerByTertipNo(Long tertipNo);
    public List<HisseSenetleri> getSenetlerByHissedar(Long hissedarId);
    public List<HisseSenetleri> olusturSenetler(List<SenetBasRequest> senetListesi, Long tertipNo);
    public HisseSenetleri save(HisseSenetleri senet);
    public Integer findMaxSeriNoByTertipNo(Long tertipNo);
    public List<HisseSenetleri> findBySpec(Specification<HisseSenetleri> spec);
    public void deleteHisseSenediBySeriNo(int seriNo);
}
