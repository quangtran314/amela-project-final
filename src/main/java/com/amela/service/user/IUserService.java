package com.amela.service.user;

import com.amela.model.user.User;
import com.amela.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
}
