package com.benz.javaproject.controller;

import com.benz.javaproject.entity.HisseSenetleri;
import com.benz.javaproject.model.SenetBasRequest;
import com.benz.javaproject.service.HisseSenediService;
import com.benz.javaproject.service.IslemlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController("/ıslemler")
public class IslemlerController {

    private final IslemlerService islemlerService;

    @Autowired
    public IslemlerController(IslemlerService islemlerService) {
        this.islemlerService = islemlerService;
    }

    @PostMapping("/senet-ver/{hissedarId}/{seriNo}")
    public ResponseEntity<String> senetVer(@PathVariable Long hissedarId, @PathVariable Long seriNo) {
        islemlerService.senetVer(hissedarId, seriNo);
        return ResponseEntity.status(HttpStatus.OK).body("Hissedara senetler başarıyla verildi.");
    }


}
