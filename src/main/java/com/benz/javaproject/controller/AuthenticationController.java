package com.benz.javaproject.controller;


import com.benz.javaproject.entity.User;
import com.benz.javaproject.model.AuthenticationRequest;
import com.benz.javaproject.repository.UserRepository;
import com.benz.javaproject.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<Map<String,Object>> register(@RequestBody User request){
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(request);
        return ResponseEntity.ok().body(Map.of("response","User added Successfully"));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String,Object>> authenticate(@RequestBody AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUserName(),
                request.getPassword()
        ));
        var user = userRepository.findByEmail(request.getUserName()).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok().body(Map.of("reponser","Successfully authenticated","token",token));


    }



}
