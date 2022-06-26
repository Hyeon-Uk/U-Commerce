package com.example.commerce.controller;

import com.example.commerce.dto.req.JoinFormDto;
import com.example.commerce.dto.req.LoginFormDto;
import com.example.commerce.entity.User;
import com.example.commerce.repository.UserRepository;
import com.example.commerce.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Controller
@Slf4j
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/join")
    public String joinForm(Model model){
        model.addAttribute("joinFormDto",new JoinFormDto());
        return "JoinForm";
    }

    public void joinFormValidator(JoinFormDto target,BindingResult bindingResult){
        log.info("password={},passwordCheck={}",target.getPassword(),target.getPasswordCheck());
        if(userRepository.findByEmail(target.getEmail()).isPresent()){
            log.info("error={}","이메일 중복");
            bindingResult.addError(new FieldError("joinFormDto","email","중복된 이메일입니다."));
        }
        String pattern = "(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}";

        if(!target.getPassword().equals(target.getPasswordCheck())){
            log.info("error={}","비밀번호 체크 확인");
            bindingResult.addError(new FieldError("joinFormDto","passwordCheck","비밀번호가 같은지 확인해주세요"));
        }

        if(!target.getPassword().matches(pattern)){
            log.info("error={}","비밀번호 조건");
            bindingResult.addError(new FieldError("joinFormDto","password","비밀번호는 소문자 대문자 특수문자가 적어도 하나씩 있어야하며, 8자리 이상이여야합니다."));
        }
    }

    @PostMapping("/join/process")
    public String joinProcess(JoinFormDto formDto, Model model, BindingResult bindingResult){
        joinFormValidator(formDto,bindingResult);
        if(bindingResult.hasErrors()){
            return "joinForm";
        }

        userService.join(formDto);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute(new LoginFormDto());//이메일 저장을 대비
        return "LoginForm";
    }

    public void loginFormValidator(LoginFormDto loginFormDto, BindingResult bindingResult){
        User user=userService.login(loginFormDto);
        if(user==null){
            log.info("로그인 validation 에러");
            bindingResult.addError(new ObjectError("loginFormDto","이메일과 패스워드를 확인해주세요"));
            return;
        }
    }

    @PostMapping("/login/process")
    public String loginProcess(LoginFormDto loginFormDto,Model model,BindingResult bindingResult){
        loginFormValidator(loginFormDto,bindingResult);
        if(bindingResult.hasErrors()){
            return "loginForm";
        }
        return "redirect:/";
    }
}
