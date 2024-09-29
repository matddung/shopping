package com.studyjun.shopping.service;

import com.studyjun.shopping.dto.TokenDto;
import com.studyjun.shopping.dto.request.LoginRequest;
import com.studyjun.shopping.dto.request.UserInfoRequest;
import com.studyjun.shopping.dto.response.AuthResponse;
import com.studyjun.shopping.entity.Token;
import com.studyjun.shopping.entity.User;
import com.studyjun.shopping.repository.TokenRepository;
import com.studyjun.shopping.repository.UserRepository;
import com.studyjun.shopping.util.DefaultAssert;
import com.studyjun.shopping.util.DefaultAuthenticationException;
import com.studyjun.shopping.util.ErrorCode;
import com.studyjun.shopping.util.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenDto tokenDto = tokenService.createToken(authentication);

        Token token = Token.builder()
                .refreshToken(tokenDto.getRefreshToken())
                .userEmail(tokenDto.getUserEmail())
                .build();

        tokenRepository.save(token);

        AuthResponse authResponse = AuthResponse.builder().accessToken(tokenDto.getAccessToken()).refreshToken(token.getRefreshToken()).build();

        return ResponseEntity.ok(authResponse);
    }

    public ResponseEntity<?> logout(String refreshToken) {
        boolean isValid = valid(refreshToken);
        DefaultAssert.isAuthentication(isValid);

        Optional<Token> token = tokenRepository.findByRefreshToken(refreshToken);
        tokenRepository.delete(token.get());

        return ResponseEntity.ok("Logout success");
    }

    public ResponseEntity<?> refresh(String refreshToken) {
        boolean isValid = valid(refreshToken);
        DefaultAssert.isAuthentication(isValid);

        Optional<Token> token = tokenRepository.findByRefreshToken(refreshToken);
        DefaultAssert.isOptionalPresent(token);

        Authentication authentication = tokenService.getAuthenticationByEmail(token.get().getUserEmail());

        Long expirationTime = tokenService.getExpiration(refreshToken);
        if (expirationTime > 0) {
            TokenDto tokenDto = tokenService.refreshToken(authentication, refreshToken);
            Token updateToken = token.get().updateRefreshToken(tokenDto.getRefreshToken());
            tokenRepository.save(updateToken);

            AuthResponse authResponse = AuthResponse.builder()
                    .accessToken(tokenDto.getAccessToken())
                    .refreshToken(updateToken.getRefreshToken())
                    .build();

            return ResponseEntity.ok(authResponse);
        }

        throw new DefaultAuthenticationException(ErrorCode.INVALID_AUTHENTICATION);
    }

    public ResponseEntity<?> modifyUserInfo(UserInfoRequest userInfoRequest, UserPrincipal userPrincipal) {
        Optional<User> userOptional = userRepository.findById(userPrincipal.getId());
        DefaultAssert.isOptionalPresent(userOptional);

        User user = userOptional.get();

        if (userInfoRequest.getPhoneNumber() != null) {
            user.setPhoneNumber(userInfoRequest.getPhoneNumber());
        }
        if (userInfoRequest.getAddress() != null) {
            user.setAddress(userInfoRequest.getAddress());
        }
        if (userInfoRequest.getBirth() != null) {
            user.setBirth(userInfoRequest.getBirth());
        }

        userRepository.save(user);

        return ResponseEntity.ok("User information modify success");
    }

    private boolean valid(String refreshToken) {
        boolean validateCheck = tokenService.validateToken(refreshToken);
        DefaultAssert.isTrue(validateCheck, "Token is not valid");

        Optional<Token> token = tokenRepository.findByRefreshToken(refreshToken);
        DefaultAssert.isOptionalPresent(token);

        Authentication authentication = tokenService.getAuthenticationByEmail(token.get().getUserEmail());
        DefaultAssert.isTrue(token.get().getUserEmail().equals(authentication.getName()), "User authentication failed");

        return true;
    }
}