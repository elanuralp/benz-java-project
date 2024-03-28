//package com.benz.javaproject.service;
//
//
//import com.benz.javaproject.entity.User;
//import com.benz.javaproject.model.JwtAuthenticationResponse;
//import com.benz.javaproject.model.RefreshTokenRequest;
//import com.benz.javaproject.model.SignUpRequest;
//import com.benz.javaproject.model.SigninRequest;
//
//public interface AuthenticationService {
//
//    User signUp(SignUpRequest signUpRequest);
//
//    public JwtAuthenticationResponse signIn(SigninRequest signinRequest);
//
//    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
//
//    void changePassword(String email, String temporaryPassword, String newPassword);
//
//    String generateTemporaryPassword();
//
//    void resetPassword(String email);
//
//
//
//}
