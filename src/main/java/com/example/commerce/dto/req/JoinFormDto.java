package com.example.commerce.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinFormDto {
    private String name;
    private String email;
    private String password;
    private String address;
    private String phone;
}
