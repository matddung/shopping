package com.studyjun.shopping.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String email;
    private String password;
    private String name;
    private String birth;
    private String address;
    private String phoneNumber;
    private String provider;
    private String providerId;
    private String role;

    @Builder
    public User(String email, String password, String name, String provider, String providerId, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }
}