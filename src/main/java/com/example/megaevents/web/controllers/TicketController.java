package com.example.megaevents.web.controllers;

import com.example.megaevents.services.models.HotelServiceModel;
import com.example.megaevents.services.models.TicketServiceModel;
import com.example.megaevents.services.services.CloudinaryService;
import com.example.megaevents.services.services.TicketService;
import com.example.megaevents.web.annotations.PageTitle;
import com.example.megaevents.web.controllers.base.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TicketController extends BaseController {

    private final ModelMapper mapper;
    private final TicketService ticketService;

    @Autowired
    public TicketController(ModelMapper mapper, TicketService ticketService) {
        this.mapper = mapper;
        this.ticketService = ticketService;
    }

    @GetMapping("/tickets")
    @PageTitle("Tickets")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView seetickets(Authentication principal, ModelAndView modelAndView){
        String username=principal.getName();

        List<TicketServiceModel> ticketsh=this.ticketService.findTicketsHotels(username);

        List<TicketServiceModel> ticketse=this.ticketService.findTicketsEvents(username);

        modelAndView.addObject("ticketsh",ticketsh);
        modelAndView.addObject("ticketse",ticketse);

        return super.view("tickets-user",modelAndView);
    }

    @GetMapping("/tickets-admin")
    @PageTitle("Admin tickets")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView adminTickets(ModelAndView modelAndView){

        List<TicketServiceModel> ticketsh=this.ticketService.findTicketsHotelsAll();

        List<TicketServiceModel> ticketse=this.ticketService.findTicketsEventsAll();

        modelAndView.addObject("ticketsh",ticketsh);
        modelAndView.addObject("ticketse",ticketse);

        return super.view("tickets-admin",modelAndView);
    }


}
