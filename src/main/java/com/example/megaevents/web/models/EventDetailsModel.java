package com.example.megaevents.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class EventDetailsModel {
    private String id;

    private String name;

    private String address;

    private String description;

    private String imageUrl;

    private String date;

    private Integer price;
}
