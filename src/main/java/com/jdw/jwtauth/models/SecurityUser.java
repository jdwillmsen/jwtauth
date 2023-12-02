package com.jdw.jwtauth.models;

import com.jdw.jwtauth.repositories.roles.RolesRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
public class SecurityUser implements UserDetails {
    private final User user;
    private final RolesRepository rolesRepository;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolesRepository.getNames(user.getRoles()).stream().map(SimpleGrantedAuthority::new).toList();
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

    public Long getUserId() {
        return user.getUserId();
    }
}
