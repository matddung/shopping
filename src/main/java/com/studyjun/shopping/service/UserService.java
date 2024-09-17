package com.studyjun.shopping.service;

import com.studyjun.shopping.domain.User;
import com.studyjun.shopping.dto.UserDto;
import com.studyjun.shopping.port.UserRepositoryPort;
import com.studyjun.shopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final UserRepositoryPort userRepositoryPort;

    public User createUser(UserDto userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .age(userDto.getAge())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .build();
        return userRepositoryPort.save(user);
    }
}