package com.example.megaevents.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class CreateHotelModel {

    private String name;

    private String address;

    private String description;

    private MultipartFile image;

    private Integer singleRoom;

    private Integer doubleRoom;

    private Integer roomForThree;

    private Integer roomForFour;

}
