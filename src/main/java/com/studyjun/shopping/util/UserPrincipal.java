package com.studyjun.shopping.util;

import com.studyjun.shopping.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Getter
public class UserPrincipal implements OAuth2User, UserDetails {
    private final String id;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;

    private UserPrincipal(String id, String email, String password, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = Collections.unmodifiableList(new ArrayList<>(authorities));
        this.attributes = attributes != null ? Collections.unmodifiableMap(new HashMap<>(attributes)) : null;
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                null
        );
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                attributes
        );
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return (String) attributes.getOrDefault("name", String.valueOf(id));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}