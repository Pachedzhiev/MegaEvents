package com.example.megaevents.web.controllers;


import com.example.megaevents.errors.EventNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class HandleAllExceptions {

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleException(Throwable exception){
        ModelAndView modelAndView=new ModelAndView("error");
        modelAndView.addObject(exception.getMessage());
        return modelAndView;
    }
}
