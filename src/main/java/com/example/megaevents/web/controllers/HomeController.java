package com.example.megaevents.web.controllers;


import com.example.megaevents.web.controllers.base.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends BaseController {

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public String getHome(){
        return "user/home";
    }

}
