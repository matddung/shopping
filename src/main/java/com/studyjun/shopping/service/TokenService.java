package com.studyjun.shopping.service;

import com.studyjun.shopping.config.OAuth2Config;
import com.studyjun.shopping.dto.TokenDto;
import com.studyjun.shopping.util.UserPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    private final OAuth2Config.OAuth2ConfigHolder oAuth2ConfigHolder;
    private final CustomUserDetailsService customUserDetailsService;

    public TokenDto createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();

        Date accessTokenExpiresIn = new Date(now.getTime() + oAuth2ConfigHolder.getAuth().getAccessTokenExpirationMSec());
        Date refreshTokenExpiresIn = new Date(now.getTime() + oAuth2ConfigHolder.getAuth().getRefreshTokenExpirationMSec());

        String accessToken = generateAccessToken(userPrincipal, accessTokenExpiresIn);
        String refreshToken = generateRefreshToken(refreshTokenExpiresIn);

        return TokenDto.builder()
                .userEmail(userPrincipal.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenDto refreshToken(Authentication authentication, String refreshToken) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();

        Date accessTokenExpiresIn = new Date(now.getTime() + oAuth2ConfigHolder.getAuth().getAccessTokenExpirationMSec());

        String accessToken = generateAccessToken(userPrincipal, accessTokenExpiresIn);

        return TokenDto.builder()
                .userEmail(userPrincipal.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean validateToken(String token) {
        try {
            byte[] keyBytes = oAuth2ConfigHolder.getAuth().getTokenSecret().getBytes(StandardCharsets.UTF_8);
            Key key = Keys.hmacShaKeyFor(keyBytes);
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException ex) {
            log.error("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException ex) {
            log.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException ex) {
            log.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException ex) {
            log.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public UsernamePasswordAuthenticationToken getAuthenticationByEmail(String email){
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public UsernamePasswordAuthenticationToken getAuthenticationById(String token){
        String userId = getUserIdFromToken(token);
        UserDetails userDetails = customUserDetailsService.loadUserById(userId);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String getUserIdFromToken(String token) {
        byte[] keyBytes = oAuth2ConfigHolder.getAuth().getTokenSecret().getBytes();
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Long getExpiration(String token) {
        byte[] keyBytes = Base64.getDecoder().decode(oAuth2ConfigHolder.getAuth().getTokenSecret());
        Key key = new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");

        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        long now = new Date().getTime();
        return (expiration.getTime() - now);
    }

    private Key getSigningKey() {
        String secretKey = oAuth2ConfigHolder.getAuth().getTokenSecret();
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateAccessToken(UserPrincipal userPrincipal, Date expiration) {
        Key key = getSigningKey();
        return Jwts.builder()
                .setSubject(String.valueOf(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private String generateRefreshToken(Date expiration) {
        Key key = getSigningKey();
        return Jwts.builder()
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}