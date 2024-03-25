package com.benz.javaproject.controller;

import com.benz.javaproject.entity.SermayeArtisi;
import com.benz.javaproject.model.SermayeArtisi.SermayeArtisSearchModel;
import com.benz.javaproject.model.SermayeArtisi.SermayeArtisiUpdateModel;
import com.benz.javaproject.model.SermayeArtisi.SermayeArtısıAddModel;
import com.benz.javaproject.service.SermayeArtisiService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/sermaye-artisi")
@SecurityRequirement(name = "Keycloak")
public class SermayeArtisiController {

    private final SermayeArtisiService sermayeArtisiService;

    @Autowired
    public SermayeArtisiController(SermayeArtisiService sermayeArtisiService) {
        this.sermayeArtisiService = sermayeArtisiService;
    }

    @GetMapping
    public ResponseEntity<List<SermayeArtisi>> getAllSermayeArtisi() {
        List<SermayeArtisi> sermayeArtisiList = sermayeArtisiService.getAllSermayeArtisi();
        return ResponseEntity.ok(sermayeArtisiList);
    }

    @GetMapping("/{tertipNo}")
    public ResponseEntity<SermayeArtisi> getSermayeArtisiById(@PathVariable Long tertipNo) {
        SermayeArtisi sermayeArtisi = sermayeArtisiService.getSermayeArtisiById(tertipNo);
        return ResponseEntity.ok(sermayeArtisi);
    }

    @PostMapping
    public ResponseEntity<SermayeArtisi> createSermayeArtisi(@RequestBody SermayeArtısıAddModel sermayeArtisi) {
        SermayeArtisi createdSermayeArtisi = sermayeArtisiService.createSermayeArtisi(sermayeArtisi);
        return ResponseEntity.ok(createdSermayeArtisi);
    }

    @PutMapping("/{tertipNo}")
    public ResponseEntity<SermayeArtisi> updateSermayeArtisi(@PathVariable Long tertipNo, @RequestBody SermayeArtisiUpdateModel updatedSermayeArtisi) {
        SermayeArtisi sermayeArtisi = sermayeArtisiService.updateSermayeArtisi(tertipNo, updatedSermayeArtisi);
        return ResponseEntity.ok(sermayeArtisi);

    }


    @PostMapping("/multiple")
    public ResponseEntity<List<SermayeArtisi>> addMultipleSermayeArtisi(@RequestBody List<SermayeArtisi> sermayeArtisiList) {
        List<SermayeArtisi> addedSermayeArtisi = sermayeArtisiService.addMultipleSermayeArtisi(sermayeArtisiList);
        return ResponseEntity.ok(addedSermayeArtisi);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SermayeArtisi>> searchSermayeArtisi(
            @RequestParam(required = false) int yil,
            @RequestParam(required = false) BigDecimal bedelliArtisMiktari,
            @RequestParam(required = false) BigDecimal bedelsizArtisMiktari,
            @RequestParam(required = false) BigDecimal sermayeArtisOrani,
            @RequestParam(required = false) BigDecimal eskiSermaye) {
        SermayeArtisSearchModel searchModel = new SermayeArtisSearchModel();
        searchModel.setYil(yil);
        searchModel.setBedelliArtisMiktari(bedelliArtisMiktari);
        searchModel.setBedelsizArtisMiktari(bedelsizArtisMiktari);
        searchModel.setSermayeArtisOrani(sermayeArtisOrani);
        searchModel.setEskiSermaye(eskiSermaye);
        List<SermayeArtisi> result = sermayeArtisiService.searchSermayeArtisi(searchModel);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{tertipNo}")
    public ResponseEntity<Void> deleteSermayeArtisi(@PathVariable Long tertipNo) {
        sermayeArtisiService.deleteSermayeArtisi(tertipNo);
        return ResponseEntity.noContent().build();
    }
}
