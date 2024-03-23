package com.benz.javaproject.service;

import com.benz.javaproject.entity.HisseSenetleri;
import com.benz.javaproject.entity.Kuponlar;

import java.math.BigDecimal;
import java.util.List;

public interface IslemlerService {
    public void senetVer(Long hissedarId, Long seriNo);
    public HisseSenetleri getAlinabilirSenetBySeriNo(Long seriNo);
    public List<Kuponlar> searchKullanilmamisKuponlarBySenet(HisseSenetleri senet);
    public void karPayiDagitimiYap(Long tertipNo, int dagitimYili, BigDecimal karPayiOrani);
    public List<Kuponlar> searchKarPayıKuponlarBySenet(HisseSenetleri senet);
}







