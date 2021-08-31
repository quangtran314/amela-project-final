package com.amela.controller;

import com.amela.exception.ForbiddenException;
import com.amela.form.LoginForm;
import com.amela.model.user.User;
import com.amela.service.role.IRoleService;
import com.amela.service.user.IUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

@Controller
public class AuthController {

    private final IRoleService roleService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final IUserService userService;

    private final AuthenticationManager authenticationManager;

    public AuthController(IRoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder, IUserService userService, AuthenticationManager authenticationManager) {
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

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
            return new ModelAndView("/login/signup");
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

        return new ModelAndView("redirect:/user/{id}/houses");
    }

    @RequestMapping("/access-denied")
    public void accessDenied() {
        throw new ForbiddenException();
    }

    // Edit user
    @GetMapping("/edit-user/{id}")
    public ModelAndView showEditForm(@PathVariable("id") long id) {
        Optional<User> user = userService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/login/editUser");
        modelAndView.addObject("user", user.orElseThrow());

        return modelAndView;
    }


    @PostMapping("/edit-user")
    public ModelAndView updateUser(@Validated @ModelAttribute("users") User user) {
        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("/login/editUser");
        modelAndView.addObject("user", user);
        modelAndView.addObject("message", "Profile updated successfully");
        return modelAndView;
    }
    
    // View user
    @GetMapping("/view-user")
    public ModelAndView viewUser(Principal principal) {
        Optional<User> user = userService.findByEmail(principal.getName());
        ModelAndView modelAndView = new ModelAndView("/login/userDetail");
        modelAndView.addObject("users", user.orElseThrow());
        return modelAndView;
    }

    // Change password
    @GetMapping("/change-password")
    public ModelAndView showForm(Principal principal) {
        Optional<User> user = userService.findByEmail(principal.getName());
        ModelAndView modelAndView = new ModelAndView("/login/changePassword");
        modelAndView.addObject("users", user.orElseThrow());

        return modelAndView;
    }

    @PostMapping("/change-password")
    public ModelAndView updatePass(@Validated @RequestParam("newPassword") String newpassword, Principal principal,
                                   @RequestParam("oldPassword") String oldPassword,
                                   @RequestParam("confirmPass") String confirmPass) {
        ModelAndView modelAndView;
        Optional<User> optionalUser = userService.findByEmail(principal.getName());
        User user = optionalUser.orElseThrow();

        if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            modelAndView = new ModelAndView("/login/changePassword");
            modelAndView.addObject("message", "Old password is incorrect ");
            return modelAndView;
        }

        if (oldPassword.equals(newpassword)) {
            modelAndView = new ModelAndView("/login/changePassword");
            modelAndView.addObject("message", "New pass must be different than the old one ! ");
            return modelAndView;
        }

        if (!newpassword.equals(confirmPass)) {
            modelAndView = new ModelAndView("/login/changePassword");
            modelAndView.addObject("message", "New password do not match  ");
            return modelAndView;
        }

        userService.savePassword(newpassword, user, bCryptPasswordEncoder);
        modelAndView = new ModelAndView("/login/changePassword");

        modelAndView.addObject("message", "Password updated successfully");
        return modelAndView;
    }
}


