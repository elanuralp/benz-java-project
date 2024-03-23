package com.benz.javaproject.service;

import com.benz.javaproject.entity.SermayeArtisi;
import com.benz.javaproject.model.SermayeArtisi.SermayeArtisSearchModel;
import com.benz.javaproject.model.SermayeArtisi.SermayeArtisiUpdateModel;
import com.benz.javaproject.model.SermayeArtisi.SermayeArtısıAddModel;

import java.util.List;

public interface SermayeArtisiService {
    public List<SermayeArtisi> getAllSermayeArtisi();
    public SermayeArtisi getSermayeArtisiById(Long tertipNo);
    public SermayeArtisi createSermayeArtisi(SermayeArtısıAddModel sermayeArtisiAddModel);
    public SermayeArtisi updateSermayeArtisi(Long tertipNo, SermayeArtisiUpdateModel updatedSermayeArtisiAddModel);
    public List<SermayeArtisi> addMultipleSermayeArtisi(List<SermayeArtisi> sermayeArtisiList);
    public List<SermayeArtisi> searchSermayeArtisi(SermayeArtisSearchModel searchModel);
    public void deleteSermayeArtisi(Long tertipNo);
    public void updateOldCapital(SermayeArtisi sermayeArtisi);
}

