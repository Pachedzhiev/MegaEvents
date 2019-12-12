package com.example.megaevents.services.services.implementations;

import com.example.megaevents.data.models.*;
import com.example.megaevents.data.repositories.*;
import com.example.megaevents.services.models.EventServiceModel;
import com.example.megaevents.services.services.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final UserProfileRepository userProfileRepository;


    @Autowired
    public EventServiceImpl(EventRepository eventRepository, ModelMapper modelMapper, UserRepository userRepository, TicketRepository ticketRepository, UserProfileRepository userProfileRepository) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.userProfileRepository = userProfileRepository;
    }


    @Override
    public EventServiceModel findEventByName(String name){
        return this.eventRepository.findByName(name).map(e->modelMapper.map(e,EventServiceModel.class)).orElseThrow(null);
    }

    @Override
    public List<EventServiceModel> findAll() {
        return this.eventRepository.findAll().stream().map(e->modelMapper.map(e,EventServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public EventServiceModel findById(String id) {
        return this.modelMapper.map(eventRepository.getById(id),EventServiceModel.class);
    }

    @Override
    public void reserve(String username, String id, Integer count) throws Exception {
        User user=this.userRepository.findUserByUsername(username).orElseThrow(() -> new Exception("User not found"));
        Event event=this.eventRepository.getById(id);



        UserProfile userProfile=user.getUserProfile();
        userProfile.getEvents().add(event);
        this.userProfileRepository.save(userProfile);

        event.getUsers().add(userProfile);


        Ticket ticket= new Ticket();
        ticket.setCount(count);
        ticket.setEvent(event);
        ticket.setUser(userProfile);
        ticket.setPrice(event.getPrice()*count);
        this.ticketRepository.save(ticket);



    }

    @Override
    public void deleteEvent(String id) {
        Event event=this.eventRepository.getById(id);
        this.eventRepository.delete(event);
    }


    @Override
    public void save(EventServiceModel eventServiceModel){
        this.eventRepository.save(this.modelMapper.map(eventServiceModel, Event.class));
    }
}



