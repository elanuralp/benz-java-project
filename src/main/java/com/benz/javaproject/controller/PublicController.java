package com.benz.javaproject.controller;

import com.benz.javaproject.service.KeycloakUserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@AllArgsConstructor
public class PublicController {
    private final KeycloakUserService keycloakUserService;
    @PutMapping("/{username}/forgot")
    public void forgotPassword(@PathVariable String username) {
        keycloakUserService.forgotPassword(username);
    }
}
