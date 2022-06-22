package com.example.commerce.service;

import com.example.commerce.dto.req.JoinFormDto;
import com.example.commerce.entity.User;
import com.example.commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
}
