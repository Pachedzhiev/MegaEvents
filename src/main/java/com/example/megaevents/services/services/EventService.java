package com.example.megaevents.services.services;

import com.example.megaevents.services.models.EventServiceModel;
import com.example.megaevents.services.models.HotelServiceModel;

import java.util.List;

public interface EventService {

   // ProductServiceModel addEvent(Prod)

    void save(EventServiceModel eventServiceModel);

    EventServiceModel findEventByName(String name);

    List<EventServiceModel> findAll();

    EventServiceModel findById(String id);

    void reserve(String username,String id,Integer count) throws Exception;
}
