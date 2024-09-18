package com.studyjun.shopping.config;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OAuth2Config {
    @Getter
    @Component
    public static class Auth {
        @Value("${jwt.secret-key}")
        private String tokenSecret;

        @Value("${jwt.access.expiration}")
        private long accessTokenExpirationMSec;

        @Value("${jwt.refresh.expiration}")
        private long refreshTokenExpirationMSec;

        @PostConstruct
        public void init() {
            if (tokenSecret == null) throw new IllegalArgumentException("Token Secret Null");
            if (accessTokenExpirationMSec == 0) throw new IllegalArgumentException("Access Token Expiration 0");
            if (refreshTokenExpirationMSec == 0) throw new IllegalArgumentException("Refresh Token Expiration 0");
        }
    }

    @Getter
    @Component
    public static class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();

        @Value("${app.oauth2.authorizedRedirectUris}")
        public void setAuthorizedRedirectUris(String authorizedRedirectUris) {
            this.authorizedRedirectUris = Arrays.asList(authorizedRedirectUris.split(","));
        }
    }

    @Getter
    @Component
    @AllArgsConstructor
    public static class OAuth2ConfigHolder {
        private final Auth auth;
        private final OAuth2 oAuth2;
    }
}
