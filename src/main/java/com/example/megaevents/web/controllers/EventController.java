package com.example.megaevents.web.controllers;

import com.example.megaevents.services.models.EventServiceModel;
import com.example.megaevents.services.services.AuthService;
import com.example.megaevents.services.services.CloudinaryService;
import com.example.megaevents.services.services.EventService;
import com.example.megaevents.web.controllers.base.BaseController;
import com.example.megaevents.web.models.CreateEventModel;
import com.example.megaevents.web.models.EventDetailsModel;
import com.example.megaevents.web.models.EventTicketModel;
import com.example.megaevents.web.models.RegisterUserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class EventController extends BaseController {

    private final ModelMapper mapper;
    private final EventService eventService;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public EventController(ModelMapper mapper, EventService eventService, CloudinaryService cloudinaryService) {
        this.mapper = mapper;
        this.eventService = eventService;
        this.cloudinaryService = cloudinaryService;
    }


    @GetMapping("/event/create")
    public String create(){
        return "create-event";
    }

    @PostMapping("/event/create")
    public String createConfirm(@ModelAttribute CreateEventModel model) throws IOException {
        EventServiceModel event=this.mapper.map(model,EventServiceModel.class);
        event.setImageUrl(cloudinaryService.upload(model.getImage()));
        this.eventService.save(event);
        return "redirect:/home";

    }

    @GetMapping("/events")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView allEvents(ModelAndView modelAndView){
        List<EventServiceModel> events=this.eventService.findAll();
        modelAndView.addObject("events",events);
        return super.view("all-events",modelAndView);

    }

    @GetMapping("/events-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView allEventsAdmin(ModelAndView modelAndView){
        List<EventServiceModel> events=this.eventService.findAll();
        modelAndView.addObject("events",events);
        return super.view("all-events-admin",modelAndView);
    }

    @PostMapping("/event/addhotel")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView eventAddHotel(@PathVariable String id,Hotel) {
        EventDetailsModel event=this.mapper.map(this.eventService.findById(id), EventDetailsModel.class));
        this.eventService.addHotel(id,hotel);
        return super.redirect("/events-admin");
    }




    @GetMapping("/events/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsEvent(@PathVariable String id,ModelAndView modelAndView){
        modelAndView.addObject("event", this.mapper.map(this.eventService.findById(id), EventDetailsModel.class));
        return super.view("event-details", modelAndView);

    }

    @PostMapping("/events/details/{id}")
    public String reserveTicket(Authentication principal, @PathVariable String id, EventTicketModel eventTicketModel) throws Exception {
        String username=principal.getName();
         Integer countOfTickets=eventTicketModel.getCountOfTickets();
        eventService.reserve(username,id,countOfTickets);
        return "redirect:/home";
    }





}
