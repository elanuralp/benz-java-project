package com.benz.javaproject.controller;

import com.benz.javaproject.entity.KarPayiDagitimi;
import com.benz.javaproject.service.KarPayiDagitimiService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/karPayiDagitimi")
@SecurityRequirement(name = "Keycloak")
public class KarPayiDagitimiController {

    private final KarPayiDagitimiService karPayiDagitimiService;

    @Autowired
    public KarPayiDagitimiController(KarPayiDagitimiService karPayiDagitimiService) {
        this.karPayiDagitimiService = karPayiDagitimiService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<KarPayiDagitimi>> getAllKarPayiDagitimi() {
        List<KarPayiDagitimi> karPayiDagitimis = karPayiDagitimiService.getAllKarPayiDagitimi();
        return new ResponseEntity<>(karPayiDagitimis, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KarPayiDagitimi> getKarPayiDagitimiById(@PathVariable("id") Long id) {
        KarPayiDagitimi karPayiDagitimi = karPayiDagitimiService.getKarPayiDagitimiById(id);
        return new ResponseEntity<>(karPayiDagitimi, HttpStatus.OK);
    }

//    @PostMapping("/add")
//    public ResponseEntity<KarPayiDagitimi> addKarPayiDagitimi(@RequestBody KarPayiDagitimi karPayiDagitimi) {
//        KarPayiDagitimi newKarPayiDagitimi = karPayiDagitimiService.saveKarPayiDagitimi(karPayiDagitimi);
//        return new ResponseEntity<>(newKarPayiDagitimi, HttpStatus.CREATED);
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteKarPayiDagitimi(@PathVariable("id") Long id) {
        karPayiDagitimiService.deleteKarPayiDagitimi(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
