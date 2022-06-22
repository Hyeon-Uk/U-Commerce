package com.example.commerce.service;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
    //임시 인코딩 함수(리버스로 반환)
    public String encodePassword(String password){
        StringBuilder sb=new StringBuilder(password);
        return sb.reverse().toString();
    }
}
