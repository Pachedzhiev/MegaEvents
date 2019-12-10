package com.example.megaevents.services.services.implementations;

import com.example.megaevents.data.models.Event;
import com.example.megaevents.data.models.Hotel;
import com.example.megaevents.data.repositories.EventRepository;
import com.example.megaevents.data.repositories.HotelRepository;
import com.example.megaevents.services.models.EventServiceModel;
import com.example.megaevents.services.models.HotelServiceModel;
import com.example.megaevents.services.services.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository, ModelMapper modelMapper) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
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
    public void save(HotelServiceModel HotelServiceModel){
        this.hotelRepository.save(this.modelMapper.map(HotelServiceModel, Hotel.class));
    }
}
