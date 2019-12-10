package com.example.megaevents.web.models;

import com.example.megaevents.data.models.Hotel;
import com.example.megaevents.data.models.UserProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateEventModel {

    private String name;

    private String address;

    private String description;

    private MultipartFile image;

    private String date;

    private Integer price;

}
