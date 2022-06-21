package com.example.commerce.controller;

import com.example.commerce.dto.req.JoinFormDto;
import com.example.commerce.entity.User;
import com.example.commerce.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/join")
    public String joinForm(){
        return "JoinForm";
    }

    @PostMapping("/join/process")
    public String joinProcess(JoinFormDto formDto){
        userService.join(formDto);
        return "redirect:/";
    }
}
