package com.example.commerce.service;

import com.example.commerce.dto.req.JoinFormDto;
import com.example.commerce.dto.req.LoginFormDto;
import com.example.commerce.entity.User;
import com.example.commerce.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User join(JoinFormDto joinFormDto){
        User user=User.builder()
                .email(joinFormDto.getEmail())
                .password(passwordEncoder.encodePassword(joinFormDto.getPassword()))
                .name(joinFormDto.getName())
                .address(joinFormDto.getAddress())
                .phone(joinFormDto.getPhone())
                .mileage(0)
                .build();

        return userRepository.save(user);
    }

    public User login(LoginFormDto loginFormDto){
        String inputEmail=loginFormDto.getEmail();
        String inputPassword=loginFormDto.getPassword();
        User user=userRepository.findByEmail(inputEmail)
                .filter(u-> passwordEncoder.encodePassword(inputPassword).equals(u.getPassword()))
                .orElse(null);


        return user;
    }
}
