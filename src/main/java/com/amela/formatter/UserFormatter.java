package com.amela.formatter;

import com.amela.model.user.User;
import com.amela.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

@Component
public class UserFormatter implements Formatter<User> {

    @Autowired
    private IUserService userService;

    @Autowired
    public UserFormatter(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public User parse(String text, Locale locale) throws ParseException {
        Optional<User> userOptional = userService.findById(Long.parseLong(text));
        return userOptional.orElse(null);
    }

    @Override
    public String print(User object, Locale locale) {
        return "[" + object.getId() + ", " +object.getEmail() + "]";
    }
}
