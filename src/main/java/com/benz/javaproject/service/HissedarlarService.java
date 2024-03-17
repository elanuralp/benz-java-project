package com.benz.javaproject.service;

import com.benz.javaproject.entity.HisseSenetleri;
import com.benz.javaproject.entity.Hissedarlar;
import com.benz.javaproject.model.hissedar.HissedarSearchModel;

import java.util.List;

public interface HissedarlarService {
    List<Hissedarlar> getAllHissedarlar();
    Hissedarlar getHissedarById(Long id);
    Hissedarlar createHissedar(Hissedarlar hissedar);
    Hissedarlar updateHissedar(Long id, Hissedarlar updatedHissedar);
    List<Hissedarlar> addMultipleHissedarlar(List<Hissedarlar> hissedarList);
    boolean isSicilNumarasiUnique(String sicilNumarasi);
    List<Hissedarlar> searchHissedarlar(HissedarSearchModel hissedarlarSearchModel);
    void deleteHissedar(Long id);
    List<HisseSenetleri> getHissedarSenetleri(Long hissedarId);
    void updateHissedar(Hissedarlar hissedar);
}
