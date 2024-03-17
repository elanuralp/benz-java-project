package com.benz.javaproject.controller;

import com.benz.javaproject.entity.HisseSenetleri;
import com.benz.javaproject.entity.Hissedarlar;
import com.benz.javaproject.enums.YatirimciTipi;
import com.benz.javaproject.model.hissedar.HissedarSearchModel;
import com.benz.javaproject.service.HissedarlarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hissedarlar")
//@PreAuthorize()
public class HissedarController {

    private final HissedarlarService hissedarlarService;

    @Autowired
    public HissedarController(HissedarlarService hissedarlarService) {
        this.hissedarlarService = hissedarlarService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Hissedarlar>> getAllHissedarlar() {
        List<Hissedarlar> hissedarlar = hissedarlarService.getAllHissedarlar();
        return ResponseEntity.ok(hissedarlar);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CUMBURLEK')")
    public ResponseEntity<Hissedarlar> getHissedarById(@PathVariable Long id) {
        Hissedarlar hissedar = hissedarlarService.getHissedarById(id);
        return ResponseEntity.ok(hissedar);
    }

    @PostMapping
    public ResponseEntity<Hissedarlar> createHissedar(@RequestBody Hissedarlar hissedar) {
        Hissedarlar createdHissedar = hissedarlarService.createHissedar(hissedar);
        if (createdHissedar != null) {
            return ResponseEntity.ok(createdHissedar);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hissedarlar> updateHissedar(@PathVariable Long id, @RequestBody Hissedarlar updatedHissedar) {
        Hissedarlar hissedar = hissedarlarService.updateHissedar(id, updatedHissedar);
        if (hissedar != null) {
            return ResponseEntity.ok(hissedar);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/multiple")
    public ResponseEntity<List<Hissedarlar>> addMultipleHissedarlar(@RequestBody List<Hissedarlar> hissedarList) {
        List<Hissedarlar> addedHissedarlar = hissedarlarService.addMultipleHissedarlar(hissedarList);
        return ResponseEntity.ok(addedHissedarlar);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Hissedarlar>> searchHissedarlar(
            @RequestParam(required = false) String unvan,
            @RequestParam(required = false) String adres,
            @RequestParam(required = false) String telefon,
            @RequestParam(required = false) YatirimciTipi yatirimciTipi,
            @RequestParam(required = false) String sicilNumarasi) {
        HissedarSearchModel searchModel = new HissedarSearchModel();
        searchModel.setUnvan(unvan);
        searchModel.setAdres(adres);
        searchModel.setTelefon(telefon);
        searchModel.setYatirimciTipi(yatirimciTipi);
        searchModel.setSicilNumarasi(sicilNumarasi);
        List<Hissedarlar> result = hissedarlarService.searchHissedarlar(searchModel);
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHissedar(@PathVariable Long id) {
        hissedarlarService.deleteHissedar(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/{id}/senetler")
    public ResponseEntity<List<HisseSenetleri>> getHissedarSenetleri(@PathVariable Long id) { //bunu burda mı yazcaz??
        List<HisseSenetleri> hissedarSenetleri = hissedarlarService.getHissedarSenetleri(id);
        return ResponseEntity.ok(hissedarSenetleri);
    }


}
