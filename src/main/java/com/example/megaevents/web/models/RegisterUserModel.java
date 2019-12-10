package com.example.megaevents.web.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserModel {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String fullName;
    private String number;

}
