package com.example.megaevents.services.models;

import com.example.megaevents.data.models.Role;
import com.example.megaevents.data.models.UserProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserServiceModel extends BaseServiceModel{

    private String username;

    private String password;

    private String email;

}
