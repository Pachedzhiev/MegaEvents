package com.example.megaevents.services.services.implementations;

import com.example.megaevents.data.models.Ticket;
import com.example.megaevents.data.repositories.TicketRepository;
import com.example.megaevents.services.models.TicketServiceModel;
import com.example.megaevents.services.services.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, ModelMapper modelMapper) {
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TicketServiceModel> findAll() {
        return this.ticketRepository.findAll().stream().map(e -> modelMapper.map(e, TicketServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public List<TicketServiceModel> findTicketsHotels(String username) {
         List<Ticket> tickets=this.ticketRepository.findTicketByUsername(username);

        List<TicketServiceModel> ticketsHotel=tickets.stream().filter(e-> e.getHotel()!=null).map(m -> modelMapper.map(m,TicketServiceModel.class)).collect(Collectors.toList());

        return ticketsHotel;
    }

    @Override
    public List<TicketServiceModel> findTicketsEvents(String username) {
        List<Ticket> tickets=this.ticketRepository.findTicketByUsername(username);

        List<TicketServiceModel> ticketsEvents=tickets.stream().filter(e-> e.getEvent()!=null).map(m -> modelMapper.map(m,TicketServiceModel.class)).collect(Collectors.toList());

        return ticketsEvents;
    }

    @Override
    public List<TicketServiceModel> findTicketsHotelsAll() {
        List<Ticket> tickets=this.ticketRepository.findAll();

        List<TicketServiceModel> ticketsHotels=tickets.stream().filter(e-> e.getHotel()!=null).map(m -> modelMapper.map(m,TicketServiceModel.class)).collect(Collectors.toList());


        return ticketsHotels;
    }

    @Override
    public List<TicketServiceModel> findTicketsEventsAll() {
        List<Ticket> tickets=this.ticketRepository.findAll();

        List<TicketServiceModel> ticketsEvents=tickets.stream().filter(e-> e.getEvent()!=null).map(m -> modelMapper.map(m,TicketServiceModel.class)).collect(Collectors.toList());


        return ticketsEvents;
    }


}
