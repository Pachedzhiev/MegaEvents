package com.example.megaevents.services.services.implementations;

import com.example.megaevents.data.models.Role;
import com.example.megaevents.data.models.User;
import com.example.megaevents.data.models.UserProfile;
import com.example.megaevents.data.repositories.RoleRepository;
import com.example.megaevents.data.repositories.UserProfileRepository;
import com.example.megaevents.data.repositories.UserRepository;
import com.example.megaevents.errors.UserTakenEcxeption;
import com.example.megaevents.services.models.UserProfileServiceModel;
import com.example.megaevents.services.models.UserServiceModel;
import com.example.megaevents.services.services.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

import static com.example.megaevents.constants.ConstantError.ERROR_USERNAME_TAKEN;

@Service
public class AuthServiceImpl implements AuthService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, UserProfileRepository userProfileRepository, ModelMapper modelMapper, RoleRepository roleRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean register(UserServiceModel user, UserProfileServiceModel userModel) {
        if (user==null || userModel==null) {
            return false;
        }
        for (int i = 0; i <this.userRepository.findAll().size() ; i++) {
            if(this.userRepository.findAll().get(i).getUsername().equals(userModel.getFullName())){
                throw new UserTakenEcxeption(ERROR_USERNAME_TAKEN);
            }
        }
            user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
            User u1 = this.modelMapper.map(user, User.class);
            u1.setRoles(getRolesForRegistration());
            UserProfile u2 = this.modelMapper.map(userModel, UserProfile.class);
            u1.setUserProfile(u2);
            this.userRepository.saveAndFlush(u1);
            this.userProfileRepository.saveAndFlush(u2);
            return true;


    }

    private Set<Role> getRolesForRegistration() {
        Set<Role> roles = new HashSet<>();

        if(this.userRepository.count() == 0) {
            roles.add(this.roleRepository.findByName("ADMIN"));
        } else {
            roles.add(this.roleRepository.findByName("USER"));

        }

        return roles;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        return this.userRepository
                .findUserByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}

