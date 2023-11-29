package com.jdw.jwtauth.services;

import com.jdw.jwtauth.models.SecurityUser;
import com.jdw.jwtauth.repositories.users.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class JwtUserDetailService implements UserDetailsService {
    private final UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        log.info("Loading user details with: emailAddress={}", emailAddress);
        return usersRepository
                .get(emailAddress)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Email address not found: emailAddress=" + emailAddress));
    }
}
