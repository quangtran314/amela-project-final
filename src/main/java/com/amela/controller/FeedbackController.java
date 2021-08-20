package com.amela.controller;

import com.amela.model.Feedback;

import com.amela.service.feedback.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class FeedbackController {

    @Autowired
    private IFeedbackService feedbackService;

    @RequestMapping("/feedback")
    public ModelAndView showListFeedback(){
        Iterable<Feedback> feedbacks = feedbackService.findAll();
        ModelAndView modelAndView = new ModelAndView("/house/detail");
        modelAndView.addObject("feedbacks", feedbacks);
        return modelAndView;

    }
}
