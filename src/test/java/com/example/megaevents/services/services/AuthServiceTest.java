package com.example.megaevents.services.services;

import com.example.megaevents.base.TestBase;
import com.example.megaevents.data.models.User;
import com.example.megaevents.data.models.UserProfile;
import com.example.megaevents.data.repositories.UserProfileRepository;
import com.example.megaevents.data.repositories.UserRepository;
import com.example.megaevents.services.models.UserProfileServiceModel;
import com.example.megaevents.services.models.UserServiceModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthServiceTest extends TestBase {

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserProfileRepository userProfileRepository;

    @MockBean
    ModelMapper modelMapper;

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    AuthService authService;


    @Test
    void add_whenUserProfileNull_shouldReturnFalse()  {
        UserServiceModel userServiceModel=new UserServiceModel();
        assertFalse(this.authService.register(userServiceModel,null));
    }

    @Test
    void add_whenUserNull_shouldReturnFalse()  {
        UserProfileServiceModel userProfileServiceModel=new UserProfileServiceModel();
        assertFalse(this.authService.register(null,userProfileServiceModel));
    }

    @Test
    void getUser_whenUserDoesNotExists_shouldThrowNotFoundException() {
        String username = "No user";

        Mockito.when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> authService.loadUserByUsername(username));
    }

    @Test
    void register_whenUserNotNull_shouldAddUser(){
        User user=new User();
        UserProfile userProfile=new UserProfile();

        UserServiceModel userServiceModel=new UserServiceModel();
        userServiceModel.setPassword("1234");
        UserProfileServiceModel userProfileServiceModel=new UserProfileServiceModel();

        Mockito.when(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword())).thenReturn("1234");
        Mockito.when(this.modelMapper.map(userServiceModel, User.class)).thenReturn(user);
        Mockito.when(this.modelMapper.map(userProfileServiceModel, UserProfile.class)).thenReturn(userProfile);
        Mockito.when(this.userRepository.save(user)).thenReturn(user);
        Mockito.when(this.userProfileRepository.save(userProfile)).thenReturn(userProfile);

        Assertions.assertTrue(this.authService.register(userServiceModel,userProfileServiceModel));

    }


}
