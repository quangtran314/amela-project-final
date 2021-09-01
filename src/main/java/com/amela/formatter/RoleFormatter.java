package com.amela.formatter;

import com.amela.model.user.Role;
import com.amela.model.user.User;
import com.amela.service.role.IRoleService;
import com.amela.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

@Component
public class RoleFormatter implements Formatter<Role> {

    @Autowired
    private IRoleService roleService;

    @Autowired
    public RoleFormatter(IRoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public Role parse(String text, Locale locale) throws ParseException {
        Optional<Role> userOptional = roleService.findById(Long.parseLong(text));
        return userOptional.orElse(null);
    }

    @Override
    public String print(Role object, Locale locale) {
        return "[" + object.getId() + ", " +object.getName() + "]";
    }
}
