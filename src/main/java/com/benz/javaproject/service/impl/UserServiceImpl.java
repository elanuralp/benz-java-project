//package com.benz.javaproject.service.impl;
//
//
//import com.benz.javaproject.entity.User;
//import com.benz.javaproject.repository.UserRepository;
//import com.benz.javaproject.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class UserServiceImpl implements UserService {
//
//    private final UserRepository userRepository;
//
//
//    @Override
//    public UserDetailsService userDetailsService(){
//        return new UserDetailsService() {
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                return userRepository.findByEmail(username)
//                        .orElseThrow( () -> new UsernameNotFoundException("User not found") );
//            }
//        };
//    }
//
//
//
//
//
//
//
//
//
//
//
//}
