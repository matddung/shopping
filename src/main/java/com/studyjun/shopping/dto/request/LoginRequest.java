package com.studyjun.shopping.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    private String email;
    private String password;


    @Builder
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}