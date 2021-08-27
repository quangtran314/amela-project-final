package com.amela.controller;

import com.amela.exception.NotFoundException;
import com.amela.exception.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(NotFoundException.class)
    private ModelAndView processNotFoundException(NotFoundException ex){
        ModelAndView modelAndView = new ModelAndView("/error/error");
        modelAndView.addObject("message", "House not found");
        return modelAndView;
    }

    @ExceptionHandler(UnauthorizedException.class)
    private ModelAndView processUnauthorizeedException(UnauthorizedException ex){
        ModelAndView modelAndView = new ModelAndView("/error/error");
        modelAndView.addObject("message", "user not login");
        return modelAndView;
    }

}
