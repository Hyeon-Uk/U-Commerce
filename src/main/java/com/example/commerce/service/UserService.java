package com.example.commerce.service;

import com.example.commerce.dto.req.JoinFormDto;
import com.example.commerce.entity.User;
import com.example.commerce.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String encodePassword(String password){
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    public User join(JoinFormDto joinFormDto){
        User user=User.builder()
                .email(joinFormDto.getEmail())
                .password(encodePassword(joinFormDto.getPassword()))
                .name(joinFormDto.getName())
                .address(joinFormDto.getAddress())
                .phone(joinFormDto.getPhone())
                .mileage(0)
                .build();

        return userRepository.save(user);
    }
}
