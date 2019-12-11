package com.example.megaevents.services.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class HotelServiceModel {
    private String id;

    private String name;

    private String address;

    private String description;

    private String imageUrl;

    private Integer singleRoom;

    private Integer doubleRoom;

    private Integer roomForThree;

    private Integer roomForFour;

    private Integer price;
}
