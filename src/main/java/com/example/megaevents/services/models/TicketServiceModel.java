package com.example.megaevents.services.models;

import com.example.megaevents.data.models.Event;
import com.example.megaevents.data.models.Hotel;
import com.example.megaevents.data.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class TicketServiceModel {

    private String id;

    private User user;

    private Integer count;

    private Integer price;

    private Event event;

    private Hotel hotel;

    private Integer singleRoom;

    private Integer doubleRoom;

    private Integer roomForThree;

    private Integer roomForFour;
}
