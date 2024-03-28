//package com.benz.javaproject.service.impl;
//
//import com.benz.javaproject.entity.User;
//import com.benz.javaproject.enums.Role;
//import com.benz.javaproject.model.JwtAuthenticationResponse;
//import com.benz.javaproject.model.RefreshTokenRequest;
//import com.benz.javaproject.model.SignUpRequest;
//import com.benz.javaproject.model.SigninRequest;
//import com.benz.javaproject.repository.UserRepository;
//import com.benz.javaproject.service.AuthenticationService;
//import com.benz.javaproject.service.JWTService;
//import lombok.RequiredArgsConstructor;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class AuthenticationServiceImpl implements AuthenticationService {
//
//    private final UserRepository userRepository;
//
//    private final PasswordEncoder passwordEncoder;
//
//    private final AuthenticationManager authenticationManager;
//
//    private final JWTService jwtService;
//
//    public User signUp(SignUpRequest signUpRequest){
//        User user = new User();
//
//        user.setEmail(signUpRequest.getEmail());
//        user.setFirstName(signUpRequest.getFirstName());
//        user.setSecondName(signUpRequest.getLastName());
//        user.setRole(Role.USER);
//        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
//
//        return userRepository.save(user);
//
//    }
//
//    public JwtAuthenticationResponse signIn(SigninRequest signinRequest){
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),
//                signinRequest.getPassword()));
//        var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(
//                () -> new IllegalArgumentException("Invalid email or password")
//        );
//
//        var jwt = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);
//
//        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
//        jwtAuthenticationResponse.setToken(jwt);
//        jwtAuthenticationResponse.setRefreshToken(refreshToken);
//        return jwtAuthenticationResponse;
//    }
//
//    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
//        String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
//        User user = userRepository.findByEmail(userEmail).orElseThrow();
//        if (jwtService.isTokenValid(refreshTokenRequest.getToken(),user)){
//            var jwt = jwtService.generateToken(user);
//
//            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
//
//            jwtAuthenticationResponse.setToken(jwt);
//            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
//            return jwtAuthenticationResponse;
//        }
//        return null;
//    }
//
//    public void resetPassword(String email) {
//        Optional<User> optionalUser = userRepository.findByEmail(email);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            String temporaryPassword = generateTemporaryPassword();
//            user.setTemporaryPassword(temporaryPassword);
//            userRepository.save(user);
//        } else {
//            throw new IllegalArgumentException("Kullanıcı bulunamadı");
//        }
//    }
//
//
//    public String generateTemporaryPassword() {
//        return RandomStringUtils.randomNumeric(6);
//    }
//
//
//    public void changePassword(String email, String temporaryPassword, String newPassword) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı"));
//        // Kullanıcının geçici şifresi doğruysa
//        if (user.getTemporaryPassword().equals(temporaryPassword)) {
//            // Yeni şifreyi kodla ve kullanıcıya ata
//            user.setPassword(passwordEncoder.encode(newPassword));
//            user.setTemporaryPassword(null); // Geçici şifreyi temizle
//            userRepository.save(user); // Veritabanına kaydet
//        } else {
//            throw new IllegalArgumentException("Geçici şifre hatalı");
//        }
//    }
//
//
//
//
//}
