package com.amela.controller;

import com.amela.form.LoginForm;
import com.amela.model.house.House;
import com.amela.model.user.User;
import com.amela.model.user.UserPrinciple;
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

@Controller
public class AuthController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/signup")
    public ModelAndView showSignupForm(){
        ModelAndView modelAndView = new ModelAndView("/login/signup");
        modelAndView.addObject("roles", roleService.findAll());
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/signup")
    public ModelAndView userSignup(@Validated @ModelAttribute("user") User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
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
    public ModelAndView getLoginForm(){
        ModelAndView modelAndView = new ModelAndView("/login/login");
        modelAndView.addObject("login", new LoginForm());
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute("login") LoginForm login){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        ModelAndView modelAndView = new ModelAndView("redirect:/houses");
        return modelAndView;
    }
}
