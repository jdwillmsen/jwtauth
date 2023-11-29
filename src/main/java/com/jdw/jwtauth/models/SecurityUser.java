package com.jdw.jwtauth.models;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
public class SecurityUser implements UserDetails {
    private final User user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(roleId -> new SimpleGrantedAuthority(roleId.toString())).toList();
    }

    @Override
    public String getPassword() {
        return user.getUserDetails().password();
    }

    @Override
    public String getUsername() {
        return user.getUserDetails().emailAddress();
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
