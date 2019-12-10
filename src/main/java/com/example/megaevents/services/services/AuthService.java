package com.example.megaevents.services.services;

import com.example.megaevents.data.models.User;
import com.example.megaevents.services.models.UserProfileServiceModel;
import com.example.megaevents.services.models.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    void register(UserServiceModel user, UserProfileServiceModel userModel);

}
