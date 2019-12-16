package com.example.megaevents.services.services.implementations;

import com.example.megaevents.data.models.*;
import com.example.megaevents.data.repositories.EventRepository;
import com.example.megaevents.data.repositories.HotelRepository;
import com.example.megaevents.data.repositories.TicketRepository;
import com.example.megaevents.data.repositories.UserRepository;
import com.example.megaevents.errors.EventNotFoundException;
import com.example.megaevents.errors.HotelNotFoundException;
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
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository, ModelMapper modelMapper, EventRepository eventRepository, UserRepository userRepository, TicketRepository ticketRepository) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }


    @Override
    public HotelServiceModel findHotelByName(String name) throws Exception {
        return this.hotelRepository.findByName(name).map(e->modelMapper.map(e,HotelServiceModel.class)).orElseThrow(()->new HotelNotFoundException("No such Hotel"));
    }

    @Override
    public List<HotelServiceModel> findAll() {
        return this.hotelRepository.findAll().stream().map(e->modelMapper.map(e,HotelServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public void addHotel(String name, String hotelName) throws Exception {
        Event event=this.eventRepository.findByName(name).orElseThrow(() -> new Exception("Event not found"));
        Hotel hotel=this.hotelRepository.findByName(hotelName).orElseThrow(()->new HotelNotFoundException("No such Hotel"));

        event.getHotels().add(hotel);

        hotel.getEvents().add(event);

        this.eventRepository.save(event);
    }

    @Override
    public List<HotelServiceModel> getHotelsByEvent(String eventName) throws Exception {
        List<HotelServiceModel> hotels=this.hotelRepository.findAllByEvents(eventName).stream().map(e -> modelMapper.map(e,HotelServiceModel.class)).collect(Collectors.toList());
        return hotels;
    }

    @Override
    public HotelServiceModel findById(String id) {
        Hotel hotel=this.hotelRepository.getById(id).orElseThrow(()->new HotelNotFoundException("No such Hotel"));
        return this.modelMapper.map(hotel, HotelServiceModel.class);
    }

    @Override
    public void reserve(String username, String id, Integer singleroom, Integer doubleroom, Integer roomForThree, Integer roomForFour) throws Exception {
        User user=this.userRepository.findUserByUsername(username).orElseThrow(() -> new Exception("User not found"));
        Hotel hotel=this.hotelRepository.getById(id).orElseThrow(()->new HotelNotFoundException("No such Hotel"));
        Integer price=hotel.getPrice();
        Integer count=singleroom+singleroom*2+roomForThree*3+roomForFour*4;

        hotel.setSingleRoom(hotel.getSingleRoom()-singleroom);
        hotel.setDoubleRoom(hotel.getDoubleRoom()-doubleroom);
        hotel.setRoomForThree(hotel.getRoomForThree()-roomForThree);
        hotel.setRoomForFour(hotel.getRoomForFour()-roomForFour);

        hotelRepository.saveAndFlush(hotel);


        Ticket ticket= new Ticket();
        ticket.setCount(count);
        ticket.setHotel(hotel);
        ticket.setUser(user.getUserProfile());
        ticket.setPrice(count*price);
        ticket.setSingleRoom(singleroom);
        ticket.setDoubleRoom(doubleroom);
        ticket.setRoomForThree(roomForThree);
        ticket.setRoomForFour(roomForFour);
        this.ticketRepository.save(ticket);
    }

    @Override
    public boolean deleteHotel(String id) throws Exception {
        if(id==null){
            return false;
        }
        Hotel hotel=this.hotelRepository.getById(id).orElseThrow(()->new HotelNotFoundException("No such Hotel"));
        List<Event> events= this.eventRepository.findByHotelName(hotel.getName());

        for (int i = 0; i <events.size() ; i++) {
            events.get(i).getHotels().remove(hotel);
        }

        this.hotelRepository.delete(hotel);
        return true;
    }


    @Override
    public boolean save(HotelServiceModel hotelServiceModel){
        if( hotelServiceModel==null){
            return false;
        }
        this.hotelRepository.save(this.modelMapper.map(hotelServiceModel, Hotel.class));
        return true;
    }
}
