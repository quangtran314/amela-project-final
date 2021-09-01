package com.amela.controller;



import com.amela.exception.ForbiddenException;
import com.amela.form.LoginForm;

import com.amela.model.user.User;

import com.amela.model.user.UserPrinciple;
import com.amela.service.role.IRoleService;
import com.amela.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

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

        ModelAndView modelAndView = new ModelAndView("redirect:/user/{id}/houses");
        return modelAndView;
    }

    @RequestMapping("/access-denied")
    public void accessDenied() {
        throw new ForbiddenException();
    }

    // edit user
    @GetMapping("/edit-user/{id}")
    public ModelAndView showEditForm(@PathVariable("id") long id) {
        Optional<User> user = userService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/login/edituser");
        modelAndView.addObject("user", user.get());

        return modelAndView;
    }


    @PostMapping("/edit-user")
    public ModelAndView updateUser(@Validated @ModelAttribute("users") User user) {
        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("/login/edituser");
        modelAndView.addObject("user", user);
        modelAndView.addObject("message", "Profile updated successfully");
        return modelAndView;
    }



// view user

    @GetMapping("/view-user")
    public ModelAndView viewUser(Principal principal) {
        Optional<User> user = userService.findByEmail(principal.getName());
        ModelAndView modelAndView = new ModelAndView("/login/userdetail");
        modelAndView.addObject("users", user.get());
        return modelAndView;
    }
 // Change password

    @GetMapping("/change-password")
    public ModelAndView showForm(Principal principal) {
        Optional<User> user = userService.findByEmail(principal.getName());
        ModelAndView modelAndView = new ModelAndView("/login/changepassword");
        modelAndView.addObject("users", user.get());

        return modelAndView;
    }
    @PostMapping("/change-password")
    public ModelAndView updatePass(@Validated @RequestParam("newPassword") String newpassword,Principal principal,
                                   @RequestParam("oldPassword") String oldpassword,
                                   @RequestParam("confirmPass") String confirmpass) {
        ModelAndView modelAndView ;
        Optional<User> optionalUser = userService.findByEmail(principal.getName());
        User user = optionalUser.get();

        if (!bCryptPasswordEncoder.matches(oldpassword,user.getPassword()))
        {
            modelAndView = new ModelAndView("/login/changepassword");
            modelAndView.addObject("message","Old password is incorrect ");
            return modelAndView;
        }
        if (oldpassword.equals(newpassword))
        {
            modelAndView = new ModelAndView("/login/changepassword");
            modelAndView.addObject("message","New pass must be different than the old one ! ");
            return modelAndView;
        }
        if (!newpassword.equals(confirmpass)){
            modelAndView = new ModelAndView("/login/changepassword");
            modelAndView.addObject("message","New password do not match  ");
            return modelAndView;
        }


        userService.savepassword(newpassword,user,bCryptPasswordEncoder);
        modelAndView = new ModelAndView("/login/changepassword");

        modelAndView.addObject("message", "Password updated successfully");
        return modelAndView;
    }

}


