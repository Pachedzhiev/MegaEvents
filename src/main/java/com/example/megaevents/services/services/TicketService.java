package com.example.megaevents.services.services;

import com.example.megaevents.services.models.TicketServiceModel;

import java.util.List;

public interface TicketService {
    
    List<TicketServiceModel> findAll();

    List<TicketServiceModel> findTicketsHotels(String username);

    List<TicketServiceModel> findTicketsEvents(String username);

    List<TicketServiceModel> findTicketsHotelsAll();

    List<TicketServiceModel> findTicketsEventsAll();
}
