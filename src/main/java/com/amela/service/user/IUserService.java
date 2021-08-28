package com.amela.service.user;

import com.amela.model.user.User;
import com.amela.service.IGeneralService;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public interface IUserService extends IGeneralService<User>, UserDetailsService {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    void savePassword(String newPassword, User user, BCryptPasswordEncoder bCryptPasswordEncoder);
}
