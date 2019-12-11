package com.example.megaevents.services.services.implementations;

import com.example.megaevents.data.models.Event;
import com.example.megaevents.data.models.Hotel;
import com.example.megaevents.data.repositories.EventRepository;
import com.example.megaevents.data.repositories.HotelRepository;
import com.example.megaevents.services.models.HotelServiceModel;
import com.example.megaevents.services.services.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final EventRepository eventRepository;


    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository, ModelMapper modelMapper, EventRepository eventRepository) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
        this.eventRepository = eventRepository;
    }


    @Override
    public HotelServiceModel findHotelByName(String name){
        return this.hotelRepository.findByName(name).map(e->modelMapper.map(e,HotelServiceModel.class)).orElseThrow(null);
    }

    @Override
    public List<HotelServiceModel> findAll() {
        return this.hotelRepository.findAll().stream().map(e->modelMapper.map(e,HotelServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public void addHotel(String name, String hotelName) throws Exception {
        Event event=this.eventRepository.findByName(name).orElseThrow(() -> new Exception("Event not found"));
        Hotel hotel=this.hotelRepository.findByName(hotelName).orElseThrow(() -> new Exception("Hotel not found"));

        event.getHotels().add(hotel);

        hotel.getEvents().add(event);

        this.eventRepository.save(event);
    }

    @Override
    public List<HotelServiceModel> getHotelsByEvent(String eventName) throws Exception {
        Event event=this.eventRepository.findByName(eventName).orElseThrow(() -> new Exception("Event not found"));

        List<HotelServiceModel> hotels=this.hotelRepository.findAllByEvents(eventName).stream().map(e -> modelMapper.map(e,HotelServiceModel.class)).collect(Collectors.toList());
        return hotels;
    }


    @Override
    public void save(HotelServiceModel HotelServiceModel){
        this.hotelRepository.save(this.modelMapper.map(HotelServiceModel, Hotel.class));
    }
}
