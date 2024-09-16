package com.studyjun.shopping.service;

import com.studyjun.shopping.entity.User;
import com.studyjun.shopping.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void createUserTest() {
        User user = new User();
        user.setName("윤준혁");
        user.setAge(29);
        userService.createUser(user);

        User user1 = new User();
        user1.setName("김대현");;
        user1.setAge(28);
        userService.createUser(user1);
    }
}