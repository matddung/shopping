package com.studyjun.shopping.handler;

import com.studyjun.shopping.config.OAuth2Config;
import com.studyjun.shopping.dto.TokenDto;
import com.studyjun.shopping.entity.Token;
import com.studyjun.shopping.repository.CustomAuthorizationRequestRepository;
import com.studyjun.shopping.repository.TokenRepository;
import com.studyjun.shopping.service.TokenService;
import com.studyjun.shopping.util.CustomCookie;
import com.studyjun.shopping.util.DefaultAssert;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.studyjun.shopping.repository.CustomAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@RequiredArgsConstructor
@Component
public class CustomSimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final OAuth2Config.OAuth2ConfigHolder oAuth2Config;
    private final TokenRepository tokenRepository;
    private final CustomAuthorizationRequestRepository customAuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultAssert.isAuthentication(!response.isCommitted());

        String targetUrl = determineTargetUrl(request, response, authentication);

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CustomCookie.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);

        DefaultAssert.isAuthentication(!(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())));

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        TokenDto tokenDto = tokenService.createToken(authentication);
        Token token = Token.builder()
                .userEmail(tokenDto.getUserEmail())
                .refreshToken(tokenDto.getRefreshToken())
                .build();
        tokenRepository.save(token);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", tokenDto.getAccessToken())
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        customAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return oAuth2Config.getOAuth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if (authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }
}