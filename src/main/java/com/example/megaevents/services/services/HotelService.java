package com.example.megaevents.services.services;

import com.example.megaevents.services.models.EventServiceModel;
import com.example.megaevents.services.models.HotelServiceModel;

import java.util.List;

public interface HotelService {

    void save(HotelServiceModel hotelServiceModel);

    HotelServiceModel findHotelByName(String name);

    List<HotelServiceModel> findAll();

    void addHotel(String name, String hotelName) throws Exception;

    List<HotelServiceModel> getHotelsByEvent(String eventName) throws Exception;

    HotelServiceModel findById(String id);

    void reserve(String username, String id, Integer singleroom, Integer doubleroom, Integer roomForThree, Integer roomForFour) throws Exception;

    void deleteHotel(String id);
}
