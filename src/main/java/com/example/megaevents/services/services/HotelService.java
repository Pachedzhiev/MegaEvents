package com.example.megaevents.services.services;

import com.example.megaevents.services.models.EventServiceModel;
import com.example.megaevents.services.models.HotelServiceModel;

import java.util.List;

public interface HotelService {

    void save(HotelServiceModel hotelServiceModel);

    HotelServiceModel findHotelByName(String name);

    List<HotelServiceModel> findAll();
}
