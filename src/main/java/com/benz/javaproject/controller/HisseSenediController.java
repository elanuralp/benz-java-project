package com.benz.javaproject.controller;

import com.benz.javaproject.entity.HisseSenetleri;
import com.benz.javaproject.enums.Role;
import com.benz.javaproject.model.hissesenedi.SenetBasRequest;
import com.benz.javaproject.service.HisseSenediService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/hisse-senetleri")

public class HisseSenediController {

    private final HisseSenediService hisseSenediService;

    @Autowired
    public HisseSenediController(HisseSenediService hisseSenetleriService) {
        this.hisseSenediService = hisseSenetleriService;
    }


    @GetMapping
    @PreAuthorize("hasRole('0')")
    public ResponseEntity<List<HisseSenetleri>> getAllHisseSenetleri() {
        List<HisseSenetleri> hisseSenetleriList = hisseSenediService.getAllHisseSenetleri();
        return ResponseEntity.ok(hisseSenetleriList);
    }

    @GetMapping("/{seriNo}")
    public ResponseEntity<HisseSenetleri> getHisseSenediBySeriNo(@PathVariable int seriNo) {
        HisseSenetleri hisseSenetleri = hisseSenediService.getHisseSenetleriBySeriNo(seriNo);
        return ResponseEntity.ok(hisseSenetleri);
    }


//    @GetMapping("/search")
//    public ResponseEntity<List<HisseSenetleri>> searchHisseSenedi(
//            @RequestParam(required = false) long tertipNo,
//            @RequestParam(required = false) long hissedarId) {
//        HisseSenediSearchModel searchModel = new HisseSenediSearchModel();
//        searchModel.setHissedarId(hissedarId);
//        searchModel.setTertipNo(tertipNo);
//        List<HisseSenetleri> result = hisseSenediService.searchHisseSenetleri(searchModel);
//        return ResponseEntity.ok(result);
//    }

    @DeleteMapping("/{seriNo}")
    public ResponseEntity<Void> deleteHisseSenedi(@PathVariable int seriNo) {
        hisseSenediService.deleteHisseSenediBySeriNo(seriNo);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/olustur")
    public ResponseEntity<List<HisseSenetleri>> olusturSenetler(@RequestBody List<SenetBasRequest> senetBasRequests, @RequestParam Long tertipNo) {
        List<HisseSenetleri> olusturulanSenetler = hisseSenediService.olusturSenetler(senetBasRequests, tertipNo);
        return ResponseEntity.ok(olusturulanSenetler);
    }

}
