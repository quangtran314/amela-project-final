package com.amela.controller;

import com.amela.form.ContractForm;
import com.amela.form.LoginForm;
import com.amela.model.Feedback;
import com.amela.model.house.House;
import com.amela.model.house.Image;
import com.amela.model.user.User;
import com.amela.model.user.UserPrinciple;
import com.amela.service.house.IHouseService;
import com.amela.service.role.IRoleService;
import com.amela.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IHouseService houseService;

    @GetMapping("/signup")
    public ModelAndView showSignupForm() {
        ModelAndView modelAndView = new ModelAndView("/login/signup");
        modelAndView.addObject("roles", roleService.findAll());
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/signup")
    public ModelAndView userSignup(@Validated @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("/login/signup");
            return modelAndView;
        }
        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("/login/signup");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("message", "New user created successfully");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView getLoginForm() {
        ModelAndView modelAndView = new ModelAndView("/login/login");
        modelAndView.addObject("login", new LoginForm());
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute("login") LoginForm login) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        ModelAndView modelAndView = new ModelAndView("redirect:/houses");
        return modelAndView;
    }

    @RequestMapping("/access-denied")
    public ModelAndView accessDenied() {
        ModelAndView modelAndView = new ModelAndView("/error/accessDenied");
        return modelAndView;
    }
    // edit user
    @GetMapping("/edit-user/{id}")
    public ModelAndView showEditForm(@PathVariable("id") long id) {
        Optional<User> user = userService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/login/edituser");
        modelAndView.addObject("users", user.get());

        return modelAndView;
    }


    @PostMapping("/edit-user")
    public ModelAndView updateUser(@ModelAttribute("users") User user) {
        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("/login/edituser");
        modelAndView.addObject("users", user);
        modelAndView.addObject("message", "Profile updated successfully");
        return modelAndView;
    }

// view user
    @GetMapping("/view-user/{id}")
    public ModelAndView viewProvince(@PathVariable("id") long id) {
        Optional<User> user = userService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/login/userdetail");
        modelAndView.addObject("users", user.get());
        return modelAndView;
    }

}


