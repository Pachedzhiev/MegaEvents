package com.example.megaevents.web.controllers;

import com.example.megaevents.errors.EventNotFoundException;
import com.example.megaevents.services.models.EventServiceModel;
import com.example.megaevents.services.models.HotelServiceModel;
import com.example.megaevents.services.services.CloudinaryService;
import com.example.megaevents.services.services.EventService;
import com.example.megaevents.services.services.HotelService;
import com.example.megaevents.web.annotations.PageTitle;
import com.example.megaevents.web.controllers.base.BaseController;
import com.example.megaevents.web.models.CreateEventModel;
import com.example.megaevents.web.models.EventAddHotelModel;
import com.example.megaevents.web.models.EventDetailsModel;
import com.example.megaevents.web.models.EventTicketModel;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import java.io.IOException;
import java.security.Principal;
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
    @PageTitle("Create Event")
    @PreAuthorize("isAuthenticated()")
    public String create(){
        return "create-event";
    }

    @PostMapping("/event/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createConfirm(@ModelAttribute CreateEventModel model) throws IOException {
        EventServiceModel event=this.mapper.map(model,EventServiceModel.class);
        event.setImageUrl(cloudinaryService.upload(model.getImage()));
        this.eventService.save(event);
        return "redirect:/home";

    }

    @GetMapping("/events")
    @PageTitle("Events")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView allEvents(ModelAndView modelAndView){
        List<EventServiceModel> events=this.eventService.findAll();
        modelAndView.addObject("events",events);
        return super.view("all-events",modelAndView);

    }

    @GetMapping("/events-admin")
    @PageTitle("Admin events")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView allEventsAdmin(ModelAndView modelAndView){
        List<EventServiceModel> events=this.eventService.findAll();
        List<HotelServiceModel> hotels=this.hotelService.findAll();
        modelAndView.addObject("events",events);
        modelAndView.addObject("hotels",hotels);
        return super.view("all-events-admin",modelAndView);
    }

    @PostMapping("/event/addhotel/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView eventAddHotel(@PathVariable String id, EventAddHotelModel eventAddHotelModel) throws Exception {
        EventDetailsModel event=this.mapper.map(this.eventService.findById(id), EventDetailsModel.class);
        String hotelName=eventAddHotelModel.getHotelName();
        this.hotelService.addHotel(event.getName(),hotelName);
        return super.redirect("/events-admin");
    }

    @PostMapping("/event/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView deleteEvent(@PathVariable String id){
        this.eventService.deleteEvent(id);
        return super.redirect("/events-admin");
    }



    @GetMapping("/events/details/{id}")
    @PageTitle("Event Details")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsEvent(@PathVariable String id,ModelAndView modelAndView){
        modelAndView.addObject("event", this.mapper.map(this.eventService.findById(id), EventDetailsModel.class));
        return super.view("event-details", modelAndView);

    }

    @PostMapping("/events/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public String reserveTicket(Authentication principal, @PathVariable String id, EventTicketModel eventTicketModel) throws Exception {
        String username=principal.getName();
         Integer countOfTickets=eventTicketModel.getCountOfTickets();
        eventService.reserve(username,id,countOfTickets);
        return "redirect:/home";
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ModelAndView handleException(EventNotFoundException exception){
        ModelAndView modelAndView=new ModelAndView("custom-error");
        modelAndView.addObject("message",exception.getMessage());
        return modelAndView;
    }





}
