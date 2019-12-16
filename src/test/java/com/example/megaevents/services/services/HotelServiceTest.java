package com.example.megaevents.services.services;

import com.example.megaevents.base.TestBase;
import com.example.megaevents.data.repositories.EventRepository;
import com.example.megaevents.data.repositories.HotelRepository;
import com.example.megaevents.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class HotelServiceTest extends TestBase {

    @MockBean
    EventRepository eventRepository;

    @MockBean
    HotelRepository hotelRepository;


    @MockBean
    UserRepository userRepository;

    @Autowired
    EventService eventService;

    @Autowired
    HotelService hotelService;
}
