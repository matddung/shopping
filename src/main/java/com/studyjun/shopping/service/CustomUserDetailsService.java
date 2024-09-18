package com.studyjun.shopping.service;

import com.studyjun.shopping.entity.User;
import com.studyjun.shopping.repository.UserRepository;
import com.studyjun.shopping.util.DefaultAssert;
import com.studyjun.shopping.util.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("CustomUserDetailsService : not found Email"));

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        DefaultAssert.isOptionalPresent(user);

        return UserPrincipal.create(user.get());
    }
}
