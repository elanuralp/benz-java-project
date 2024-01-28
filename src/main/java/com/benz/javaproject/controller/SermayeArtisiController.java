package com.benz.javaproject.controller;

import com.benz.javaproject.entity.SermayeArtisi;
import com.benz.javaproject.exception.AbuzerException;
import com.benz.javaproject.exception.MyException;
import com.benz.javaproject.service.SermayeArtisiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sermaye-artisi")
public class SermayeArtisiController {
    private final SermayeArtisiService sermayeArtisiService;

    @Autowired
    public SermayeArtisiController(SermayeArtisiService sermayeArtisiService) {
        this.sermayeArtisiService = sermayeArtisiService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SermayeArtisi> getSermayeArtisiById(@PathVariable Long id) {
        Optional<SermayeArtisi> sermayeArtisiOptional = sermayeArtisiService.getSermayeArtisiById(id);
        return sermayeArtisiOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<SermayeArtisi>> getAllSermayeArtisi(){
        List<SermayeArtisi> sermayeArtisiList = sermayeArtisiService.getAllSermayeArtisi();
        return ResponseEntity.ok(sermayeArtisiList);
    }

    @PostMapping("/add")
    public ResponseEntity<SermayeArtisi> addSermayeArtisi(@RequestBody SermayeArtisi sermayeArtisi) {
        SermayeArtisi savedSermayeArtisi = sermayeArtisiService.saveSermayeArtisi(sermayeArtisi);
        return ResponseEntity.ok(savedSermayeArtisi);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSermayeArtisiById(@PathVariable Long id) {
        sermayeArtisiService.deleteSermayeArtisiById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/abuzer")
    public ResponseEntity<Void> abuzer() {
        throw new AbuzerException();
    }
}
