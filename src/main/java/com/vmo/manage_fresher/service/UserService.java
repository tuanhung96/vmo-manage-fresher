package com.vmo.manage_fresher.service;

import com.vmo.manage_fresher.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByEmail(String mail);
}
