//package com.benz.javaproject.service;
//
//import com.benz.javaproject.entity.User;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public interface JWTService {
//
//    String extractUsername(String token);
//
//    String generateToken(UserDetails userDetails);
//
//    boolean isTokenValid(String token , UserDetails userDetails);
//
//    String generateRefreshToken(Map<String,Object> extraClaims, UserDetails userDetails);
//
//}
