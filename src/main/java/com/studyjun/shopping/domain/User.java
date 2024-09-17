package com.studyjun.shopping.domain;


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
    private String name;
    private Integer age;
    private String phoneNumber;
    private String email;
    private String provider;
    private String providerId;
    private String role;

    @Builder
    public User(String name, Integer age, String phoneNumber, String email, String provider, String providerId, String role) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }
}