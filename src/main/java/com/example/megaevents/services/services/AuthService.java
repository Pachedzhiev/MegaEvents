package com.example.megaevents.services.services;

import com.example.megaevents.data.models.User;
import com.example.megaevents.services.models.UserProfileServiceModel;
import com.example.megaevents.services.models.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthService extends UserDetailsService {

    boolean register(UserServiceModel user, UserProfileServiceModel userModel);

    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;


}
