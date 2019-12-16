package com.example.megaevents.web.controllers;

import com.example.megaevents.data.models.Hotel;
import com.example.megaevents.errors.EventNotFoundException;
import com.example.megaevents.services.models.EventServiceModel;
import com.example.megaevents.services.models.HotelServiceModel;
import com.example.megaevents.services.services.CloudinaryService;
import com.example.megaevents.services.services.EventService;
import com.example.megaevents.services.services.HotelService;
import com.example.megaevents.web.annotations.PageTitle;
import com.example.megaevents.web.controllers.base.BaseController;
import com.example.megaevents.web.models.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class HotelController extends BaseController {


    private final ModelMapper mapper;
    private final HotelService hotelService;
    private final CloudinaryService cloudinaryService;
    private final EventService eventService;

    @Autowired
    public HotelController(ModelMapper mapper, HotelService hotelService, CloudinaryService cloudinaryService, EventService eventService) {
        this.mapper = mapper;
        this.hotelService = hotelService;
        this.cloudinaryService = cloudinaryService;
        this.eventService = eventService;
    }


    @GetMapping("/hotel/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PageTitle("Create hotel")
    public String create(){
        return "add-hotel";
    }

    @PostMapping("/hotel/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createConfirm(@ModelAttribute CreateHotelModel model) throws IOException {
        HotelServiceModel hotel = this.mapper.map(model, HotelServiceModel.class);
        hotel.setImageUrl(cloudinaryService.upload(model.getImage()));
        this.hotelService.save(hotel);
        return "redirect:/home";

    }

    @GetMapping("/hotels")
    @PageTitle("Hotels")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView allHotels(ModelAndView modelAndView){
        List<HotelServiceModel> hotels=this.hotelService.findAll();
        modelAndView.addObject("hotels",hotels);
        return super.view("all-hotels",modelAndView);

    }

    @GetMapping("/hotel/chooseevent")
    @PageTitle("Choose event")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView chooseev(ModelAndView modelAndView){
         List<EventServiceModel> events=this.eventService.findAll();
         modelAndView.addObject("events",events);
        return super.view("choose-hotel",modelAndView);
    }

    @PostMapping("/hotel/chooseevent")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView chooseEvent(EventForHotelModel event, ModelAndView modelAndView) throws Exception {
        String eventName=event.getEvent();
        List<HotelServiceModel> hotels=this.hotelService.getHotelsByEvent(eventName);
        modelAndView.addObject("hotels",hotels);
        return super.view("all-hotels",modelAndView);
    }


    @GetMapping("/hotels/details/{id}")
    @PageTitle("Hotel details")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsHotel(@PathVariable String id, ModelAndView modelAndView){
        modelAndView.addObject("hotel", this.mapper.map(this.hotelService.findById(id), HotelDetailsModel.class));
        return super.view("hotel-details", modelAndView);

    }

    @PostMapping("/hotels/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public String reserveTicket(Authentication principal, @PathVariable String id, HotelDetailsModel hotelDetailsModel) throws Exception {
        String username=principal.getName();
        Integer singleroom=hotelDetailsModel.getSingleRoom();
        Integer doubleroom=hotelDetailsModel.getDoubleRoom();
        Integer roomForThree=hotelDetailsModel.getRoomForThree();
        Integer roomForFour=hotelDetailsModel.getRoomForFour();
        hotelService.reserve(username,id,singleroom,doubleroom,roomForThree,roomForFour);
        return "redirect:/home";
    }

    @GetMapping("/hotels-admin")
    @PageTitle("Admin hotels")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView allEventsAdmin(ModelAndView modelAndView){
        List<HotelServiceModel> hotels=this.hotelService.findAll();
        modelAndView.addObject("hotels",hotels);
        return super.view("all-hotels-admin",modelAndView);
    }

    @PostMapping("/hotel/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView deleteHotel(@PathVariable String id) throws Exception {
        this.hotelService.deleteHotel(id);
        return super.redirect("/hotels-admin");
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ModelAndView handleException(EventNotFoundException exception){
        ModelAndView modelAndView=new ModelAndView("custom-error");
        modelAndView.addObject("message",exception.getMessage());
        return modelAndView;
    }

}
