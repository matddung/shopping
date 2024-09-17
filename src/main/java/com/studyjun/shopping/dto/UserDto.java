package com.studyjun.shopping.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String name;
    private Integer age;
    private String phoneNumber;
    private String email;
}