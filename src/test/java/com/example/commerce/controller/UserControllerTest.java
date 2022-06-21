package com.example.commerce.controller;

import com.example.commerce.dto.req.JoinFormDto;
import com.example.commerce.entity.User;
import com.example.commerce.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("Join Complete")
    public void saveComplete() throws Exception{
        /*
        * 정상적으로 조건을 지키면 save가 된 뒤, 정보 저장
        * */
        MultiValueMap<String,String> user=new LinkedMultiValueMap<>();
        user.add("email","test1@naver.com");
        user.add("name","kim");
        user.add("password","abcdefg123!");
        user.add("address","home");
        user.add("phone","000-1111-2222");

        String content=objectMapper.writeValueAsString(user);
        System.out.println("content = " + content);

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

}