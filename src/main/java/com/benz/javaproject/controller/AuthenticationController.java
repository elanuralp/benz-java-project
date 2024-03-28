//package com.benz.javaproject.controller;
//
//
//import com.benz.javaproject.entity.User;
//import com.benz.javaproject.model.JwtAuthenticationResponse;
//import com.benz.javaproject.model.RefreshTokenRequest;
//import com.benz.javaproject.model.SignUpRequest;
//import com.benz.javaproject.model.SigninRequest;
//import com.benz.javaproject.service.AuthenticationService;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/auth")
//@RequiredArgsConstructor
//@SecurityRequirement(name = "Keycloak")
//public class AuthenticationController {
//
//    private final AuthenticationService authenticationService;
//
//    @PostMapping("/signUp")
//    public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest){
//        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
//    }
//
//    @PostMapping("/signIn")
//    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SigninRequest signinRequest){
//        return ResponseEntity.ok(authenticationService.signIn(signinRequest));
//    }
//
//    @PostMapping("/refresh")
//    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
//        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
//    }
//
//    @PostMapping("/resetPassword")
//    public ResponseEntity<String> resetPassword(@RequestParam String email) {
//        authenticationService.resetPassword(email);
//        return ResponseEntity.ok("Geçici şifre oluşturuldu ve kullanıcıya atandı.");
//    }
//
//    @PostMapping("/changePassword")
//    public ResponseEntity<String> changePassword(@RequestParam String email, @RequestParam String temporaryPassword, @RequestParam String newPassword) {
//        authenticationService.changePassword(email, temporaryPassword, newPassword);
//        return ResponseEntity.ok("Şifre başarıyla değiştirildi.");
//    }
//}
