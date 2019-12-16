package com.example.megaevents.services.services;

import com.example.megaevents.base.TestBase;
import com.example.megaevents.data.models.Event;
import com.example.megaevents.data.models.Hotel;
import com.example.megaevents.data.models.Ticket;
import com.example.megaevents.data.models.User;
import com.example.megaevents.data.repositories.EventRepository;
import com.example.megaevents.data.repositories.HotelRepository;
import com.example.megaevents.data.repositories.TicketRepository;
import com.example.megaevents.data.repositories.UserRepository;
import com.example.megaevents.services.models.TicketServiceModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TicketServiceTest extends TestBase {

    @MockBean
    TicketRepository ticketRepository;

    @MockBean
    HotelRepository hotelRepository;

    @MockBean
    EventRepository eventRepository;

    @MockBean
    UserRepository userRepository;
    
    @MockBean
    ModelMapper modelMapper;

    @Autowired
    EventService eventService;

    @Autowired
    HotelService hotelService;

    @Autowired
    TicketService ticketService;


    @Test
    void findAll_whenHaveTwo_ReturnThem(){
        Ticket ticket=new Ticket();
        ticket.setCount(2);

        Ticket ticket2=new Ticket();
        ticket2.setCount(23);

        List<Ticket> tickets=new ArrayList<>();
        tickets.add(ticket);
        tickets.add(ticket2);

        Mockito.when(ticketRepository.findAll()).thenReturn(tickets);

        assertEquals(tickets.size(),ticketRepository.findAll().size());
    }

    @Test
    void findAll_whenEventsAreNull_ReturnNull(){
        List<Ticket> tickets=new ArrayList<>();

        Mockito.when(ticketRepository.findAll()).thenReturn(tickets);

        assertEquals(tickets.size(),ticketRepository.findAll().size());
    }

    @Test
    void findTicketsForHotel_whenUserIsNotNull_ReturnThem(){
        User user=new User();
        user.setUsername("user");

        Hotel hotel=new Hotel();
        hotel.setName("hotel");

        Ticket ticket=new Ticket();
        ticket.setUser(user.getUserProfile());
        ticket.setHotel(hotel);

        List<Ticket> tickets=new ArrayList<>();
        tickets.add(ticket);

        List<TicketServiceModel> ticketss=tickets.stream().map(e->modelMapper.map(e,TicketServiceModel.class)).collect(Collectors.toList());

        Mockito.when(ticketRepository.findTicketByUsername(user.getUsername())).thenReturn((tickets));

        assertEquals(this.ticketService.findTicketsHotels(user.getUsername()),ticketss);

    }

    @Test
    void findTicketsForEvent_whenUserIsNotNull_ReturnThem(){
        User user=new User();
        user.setUsername("user");

        Event event=new Event();
        event.setName("event");

        Ticket ticket=new Ticket();
        ticket.setUser(user.getUserProfile());
        ticket.setEvent(event);

        List<Ticket> tickets=new ArrayList<>();
        tickets.add(ticket);

        List<TicketServiceModel> ticketss=tickets.stream().map(e->modelMapper.map(e,TicketServiceModel.class)).collect(Collectors.toList());

        Mockito.when(ticketRepository.findTicketByUsername(user.getUsername())).thenReturn((tickets));

        assertEquals(this.ticketService.findTicketsEvents(user.getUsername()),ticketss);

    }
    @Test
    void findTicketsHotelsAll_whenIsNull_ReturnThem(){
        Ticket ticket=new Ticket();
        Ticket ticket2=new Ticket();

        List<Ticket> tickets=new ArrayList<>();

        List<TicketServiceModel> ticketss=tickets.stream().map(e->modelMapper.map(e,TicketServiceModel.class)).collect(Collectors.toList());

        Mockito.when(ticketRepository.findAll()).thenReturn((tickets));

        assertEquals(this.ticketService.findTicketsHotelsAll(),ticketss);
    }

    @Test
    void findTicketsHotelsAll_whenIsNotNull_ReturnThem(){
        Ticket ticket=new Ticket();
        Ticket ticket2=new Ticket();

        Hotel hotel=new Hotel();
        Hotel hotel2=new Hotel();

        ticket.setHotel(hotel);
        ticket2.setHotel(hotel2);

        List<Ticket> tickets=new ArrayList<>();
        tickets.add(ticket);
        tickets.add(ticket2);

        List<TicketServiceModel> ticketss=tickets.stream().map(e->modelMapper.map(e,TicketServiceModel.class)).collect(Collectors.toList());

        Mockito.when(ticketRepository.findAll()).thenReturn((tickets));

        assertEquals(this.ticketService.findTicketsHotelsAll(),ticketss);
    }

    @Test
    void findTicketsEventsAll_whenIsNull_ReturnThem(){
        Ticket ticket=new Ticket();
        Ticket ticket2=new Ticket();

        List<Ticket> tickets=new ArrayList<>();

        List<TicketServiceModel> ticketss=tickets.stream().map(e->modelMapper.map(e,TicketServiceModel.class)).collect(Collectors.toList());

        Mockito.when(ticketRepository.findAll()).thenReturn((tickets));

        assertEquals(this.ticketService.findTicketsEventsAll(),ticketss);
    }

    @Test
    void findTicketsEventsAll_whenIsNotNull_ReturnThem(){
        Ticket ticket=new Ticket();
        Ticket ticket2=new Ticket();

        Event event=new Event();
        Event event2=new Event();

        ticket.setEvent(event);
        ticket2.setEvent(event2);

        List<Ticket> tickets=new ArrayList<>();
        tickets.add(ticket);
        tickets.add(ticket2);

        List<TicketServiceModel> ticketss=tickets.stream().map(e->modelMapper.map(e,TicketServiceModel.class)).collect(Collectors.toList());

        Mockito.when(ticketRepository.findAll()).thenReturn((tickets));

        assertEquals(this.ticketService.findTicketsEventsAll(),ticketss);
    }




}
