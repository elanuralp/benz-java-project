package com.benz.javaproject.controller;

import com.benz.javaproject.entity.HisseSenetleri;
import com.benz.javaproject.entity.Kuponlar;
import com.benz.javaproject.service.KuponlarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kuponlar")
public class KuponlarController {

    private final KuponlarService kuponlarService;

    @Autowired
    public KuponlarController(KuponlarService kuponlarService) {
        this.kuponlarService = kuponlarService;
    }

    @GetMapping("/pay-alma/{tertipNo}")
    public List<Kuponlar> getPayAlmaKuponlarByTertipNo(@PathVariable Long tertipNo) {
        return kuponlarService.searchPayAlmaKuponuByTertipNo(tertipNo);
    }
}
