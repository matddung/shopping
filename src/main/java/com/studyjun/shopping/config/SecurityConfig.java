package com.studyjun.shopping.config;

import com.studyjun.shopping.handler.CustomSimpleUrlAuthenticationFailureHandler;
import com.studyjun.shopping.handler.CustomSimpleUrlAuthenticationSuccessHandler;
import com.studyjun.shopping.repository.CustomAuthorizationRequestRepository;
import com.studyjun.shopping.service.CustomOAuth2Service;
import com.studyjun.shopping.service.CustomUserDetailsService;
import com.studyjun.shopping.util.CustomAuthenticationEntryPoint;
import com.studyjun.shopping.util.CustomOncePerRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuth2Service oAuth2Service;
    private final CustomSimpleUrlAuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final CustomSimpleUrlAuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final CustomAuthorizationRequestRepository customAuthorizationRequestRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .headers(header -> header.frameOptions().sameOrigin())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(h -> h.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .oauth2Login(o -> o
                        .authorizationEndpoint(a -> a
                                .baseUri("/oauth2/authorize")
                                .authorizationRequestRepository(customAuthorizationRequestRepository))
                        .redirectionEndpoint(r -> r
                                .baseUri("/login/oauth2/code/*"))
                        .userInfoEndpoint(u -> u
                                .userService(oAuth2Service))
                        .successHandler(oAuth2AuthenticationSuccessHandler)
                        .failureHandler(oAuth2AuthenticationFailureHandler))
                .addFilterBefore(customOncePerRequestFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public CustomOncePerRequestFilter customOncePerRequestFilter() {
        return new CustomOncePerRequestFilter();
    }
}