package com.benz.javaproject.controller;


import com.benz.javaproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hi, User");
    }


    @PatchMapping
    public ResponseEntity<?> changePassword(
        @RequestBody ChangePasswordRequest request
    ){

    }
}
