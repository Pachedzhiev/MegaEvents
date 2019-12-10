package com.example.megaevents.web.controllers;

import com.example.megaevents.services.models.UserProfileServiceModel;
import com.example.megaevents.services.models.UserServiceModel;
import com.example.megaevents.services.services.AuthService;
import com.example.megaevents.web.controllers.base.BaseController;
import com.example.megaevents.web.models.RegisterUserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController extends BaseController {
    private final ModelMapper modelMapper;
    private final AuthService authService;

    @Autowired
    public AuthController(ModelMapper modelMapper, AuthService authService) {
        this.modelMapper = modelMapper;
        this.authService = authService;
    }


    @GetMapping("/register")
    public ModelAndView getRegisterForm() {
        return new ModelAndView("/auth/register");
    }


    @GetMapping("/login")
    public String getLoginPage() {
        return "/auth/login";
    }


    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute RegisterUserModel model) {
        ModelAndView modelAndView = new ModelAndView();

        if (!model.getPassword().equals(model.getConfirmPassword())) {
            modelAndView.setViewName("/auth/register");
            return modelAndView;
        }

        UserServiceModel userO = this.modelMapper.map(model, UserServiceModel.class);
        UserProfileServiceModel userP = this.modelMapper.map(model, UserProfileServiceModel.class);

        this.authService.register(userO, userP);

        modelAndView.setViewName("/auth/login");
        return modelAndView;
    }

}
