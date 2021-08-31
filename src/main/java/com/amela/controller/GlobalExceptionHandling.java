package com.amela.controller;

import com.amela.exception.ForbiddenException;
import com.amela.exception.NotFoundException;
import com.amela.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(basePackages = {"com.amela.configuration.secutiry", "com.amela.controller"})
public class GlobalExceptionHandling {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    private ModelAndView processNotFoundException(NotFoundException ex){
        ModelAndView modelAndView = new ModelAndView("/error/404-error");
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    private ModelAndView processUnauthorizeedException(UnauthorizedException ex){
        ModelAndView modelAndView = new ModelAndView("/error/error");
        modelAndView.addObject("message", "user not login");
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    private ModelAndView processForbiddenException(ForbiddenException ex){
        ModelAndView modelAndView = new ModelAndView("/error/403-error");
        return modelAndView;
    }
}
