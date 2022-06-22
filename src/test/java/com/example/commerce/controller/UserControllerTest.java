package com.example.commerce.controller;

import com.example.commerce.entity.User;
import com.example.commerce.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("Join Complete")
    @Transactional
    public void saveComplete() throws Exception{
        /*
        * 정상적으로 조건을 지키면 save가 된 뒤, 정보 저장
        * */
        MultiValueMap<String,String> user=new LinkedMultiValueMap<>();
        user.add("email","test1@naver.com");
        user.add("name","kim");
        user.add("password","Abcdefg123!");
        user.add("passwordCheck","Abcdefg123!");
        user.add("address","home");
        user.add("phone","000-1111-2222");

        mockMvc.perform(post("/users/join/process")
                        .contentType(MediaType.ALL)
                        .params(user))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andDo(print());

        //저장이 됐는지 확인
        List<User> users=userRepository.findAll();
        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Join Fail ( Email Duplicate ) ")
    @Transactional
    public void emailDuplicate() throws Exception {
        /**
         * 이메일 중복시 에러메세지를 가지고 joinForm으로 돌아가기
         */

        User user=new User("kim","test1@gmail.com","test","test","test",0);
        userRepository.save(user);


        MultiValueMap<String,String> user1=new LinkedMultiValueMap<>();
        user1.add("email","test1@gmail.com");
        user1.add("name","kim");
        user1.add("password","Abcdefg123!");
        user1.add("passwordCheck","Abcdefg123!");
        user1.add("address","home");
        user1.add("phone","000-1111-2222");

        MvcResult result = mockMvc.perform(post("/users/join/process").params(user1))
                .andExpect(model().hasErrors())
                .andReturn();

        assertThat(userRepository.findAll().size()).isEqualTo(1);//저장안됨
    }

    @Test
    @DisplayName("Join Fail ( password not same ) ")
    @Transactional
    public void passwordCheck() throws Exception {
        /**
         * 비밀번호와 비밀번호 확인을 다르게하면 오류
         */
        MultiValueMap<String,String> user1=new LinkedMultiValueMap<>();
        user1.add("email","test1@naver.com");
        user1.add("name","kim");
        user1.add("password","Abcdefg123!");
        user1.add("password","Acsdfasdfa2#");
        user1.add("address","home");
        user1.add("phone","000-1111-2222");

        MvcResult result = mockMvc.perform(post("/users/join/process").params(user1))
                .andExpect(model().hasErrors())
                .andReturn();
        assertThat(userRepository.findAll().size()).isEqualTo(0);//저장안됨
    }

    @Test
    @DisplayName("Join Fail ( password can't accept ) ")
    @Transactional
    public void passwordCanNotPass() throws Exception {
        /**
         * 비밀번호 조건을 못맞추면 오류
         */
        MultiValueMap<String,String> user1=new LinkedMultiValueMap<>();
        user1.add("email","test1@naver.com");
        user1.add("name","kim");
        user1.add("password","abc1!");
        user1.add("password","abc1!");
        user1.add("address","home");
        user1.add("phone","000-1111-2222");

        MvcResult result = mockMvc.perform(post("/users/join/process").params(user1))
                .andExpect(model().hasErrors())
                .andReturn();
        assertThat(userRepository.findAll().size()).isEqualTo(0);//저장안됨
    }
}