package com.ato.bananaleaf.service;

import com.ato.bananaleaf.dto.UserRegistrationDto;
import com.ato.bananaleaf.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByEmail(String email);

    User save(UserRegistrationDto registration);
}
