package com.example.commerce.dto.req;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginFormDto {
    private String email;
    private String password;
}
