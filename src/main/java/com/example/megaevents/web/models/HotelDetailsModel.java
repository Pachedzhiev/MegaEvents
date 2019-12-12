package com.example.megaevents.web.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.SecondaryTable;

@Getter
@Setter
@NoArgsConstructor
public class HotelDetailsModel {
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
