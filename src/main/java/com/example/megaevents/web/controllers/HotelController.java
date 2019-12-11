package com.example.megaevents.web.controllers;

import com.example.megaevents.services.models.EventServiceModel;
import com.example.megaevents.services.models.HotelServiceModel;
import com.example.megaevents.services.services.CloudinaryService;
import com.example.megaevents.services.services.EventService;
import com.example.megaevents.services.services.HotelService;
import com.example.megaevents.web.controllers.base.BaseController;
import com.example.megaevents.web.models.CreateHotelModel;
import com.example.megaevents.web.models.EventForHotelModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String create(){
        return "add-hotel";
    }

    @PostMapping("/hotel/create")
    public String createConfirm(@ModelAttribute CreateHotelModel model) throws IOException {
        HotelServiceModel hotel = this.mapper.map(model, HotelServiceModel.class);
        hotel.setImageUrl(cloudinaryService.upload(model.getImage()));
        this.hotelService.save(hotel);
        return "redirect:/home";

    }

    @GetMapping("/hotels")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView allHotels(ModelAndView modelAndView){
        List<HotelServiceModel> hotels=this.hotelService.findAll();
        modelAndView.addObject("hotels",hotels);
        return super.view("all-hotels",modelAndView);

    }

    @GetMapping("/hotel/chooseevent")
    public ModelAndView chooseev(ModelAndView modelAndView){
         List<EventServiceModel> events=this.eventService.findAll();
         modelAndView.addObject("events",events);
        return super.view("choose-hotel",modelAndView);
    }

    @PostMapping("/hotel/chooseevent")
    public ModelAndView chooseEvent(EventForHotelModel event, ModelAndView modelAndView) throws Exception {
        String eventName=event.getEvent();
        List<HotelServiceModel> hotels=this.hotelService.getHotelsByEvent(eventName);
        modelAndView.addObject("hotels",hotels);
        return super.view("all-hotels",modelAndView);
    }
}
