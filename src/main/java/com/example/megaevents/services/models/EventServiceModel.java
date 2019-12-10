package com.example.megaevents.services.models;

import com.example.megaevents.data.models.Hotel;
import com.example.megaevents.data.models.UserProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EventServiceModel {
    private String id;

    private String name;

    private String address;

    private String description;

    private String imageUrl;

    private String date;

    private Integer price;
}
