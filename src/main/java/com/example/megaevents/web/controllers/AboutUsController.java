package com.example.megaevents.web.controllers;

import com.example.megaevents.web.annotations.PageTitle;
import com.example.megaevents.web.controllers.base.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutUsController extends BaseController {


    @GetMapping("/about-us")
    @PageTitle("About Mega Events")
    @PreAuthorize("isAuthenticated()")
    public String getIndex(){
        return "about-us";
    }
}
