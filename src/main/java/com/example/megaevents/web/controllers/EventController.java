package com.example.megaevents.web.controllers;

import com.example.megaevents.services.models.EventServiceModel;
import com.example.megaevents.services.models.HotelServiceModel;
import com.example.megaevents.services.services.CloudinaryService;
import com.example.megaevents.services.services.EventService;
import com.example.megaevents.services.services.HotelService;
import com.example.megaevents.web.controllers.base.BaseController;
import com.example.megaevents.web.models.CreateEventModel;
import com.example.megaevents.web.models.EventAddHotelModel;
import com.example.megaevents.web.models.EventDetailsModel;
import com.example.megaevents.web.models.EventTicketModel;
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

import java.io.IOException;
import java.util.List;

@Controller
public class EventController extends BaseController {

    private final ModelMapper mapper;
    private final EventService eventService;
    private final CloudinaryService cloudinaryService;
    private final HotelService hotelService;

    @Autowired
    public EventController(ModelMapper mapper, EventService eventService, CloudinaryService cloudinaryService, HotelService hotelService) {
        this.mapper = mapper;
        this.eventService = eventService;
        this.cloudinaryService = cloudinaryService;
        this.hotelService = hotelService;
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
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allEventsAdmin(ModelAndView modelAndView){
        List<EventServiceModel> events=this.eventService.findAll();
        List<HotelServiceModel> hotels=this.hotelService.findAll();
        modelAndView.addObject("events",events);
        modelAndView.addObject("hotels",hotels);
        return super.view("all-events-admin",modelAndView);
    }

    @PostMapping("/event/addhotel/{id}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView eventAddHotel(@PathVariable String id, EventAddHotelModel eventAddHotelModel) throws Exception {
        EventDetailsModel event=this.mapper.map(this.eventService.findById(id), EventDetailsModel.class);
        String hotelName=eventAddHotelModel.getHotelName();
        this.hotelService.addHotel(event.getName(),hotelName);
        return super.redirect("/events-admin");
    }

    @PostMapping("/event/delete/{id}")
    public ModelAndView deleteEvent(@PathVariable String id){
        this.eventService.deleteEvent(id);
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
