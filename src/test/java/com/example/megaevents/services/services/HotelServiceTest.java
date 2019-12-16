package com.example.megaevents.services.services;

import com.example.megaevents.base.TestBase;
import com.example.megaevents.data.models.Event;
import com.example.megaevents.data.models.Hotel;
import com.example.megaevents.data.repositories.EventRepository;
import com.example.megaevents.data.repositories.HotelRepository;
import com.example.megaevents.data.repositories.UserRepository;
import com.example.megaevents.errors.HotelNotFoundException;
import com.example.megaevents.services.models.HotelServiceModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HotelServiceTest extends TestBase {

    @MockBean
    EventRepository eventRepository;

    @MockBean
    HotelRepository hotelRepository;


    @MockBean
    UserRepository userRepository;

    @Autowired
    EventService eventService;

    @Autowired
    HotelService hotelService;


    @Test
    void save_whenHotelServiceModelIsNull_shouldReturnFalse() {

        assertFalse(this.hotelService.save(null));
    }

    @Test
    void save_whenHotelServiceModelIsNotNull_shouldReturnFalse() {
        HotelServiceModel hotelServiceModel=new HotelServiceModel();
        hotelServiceModel.setName("hotel");
        assertTrue(this.hotelService.save(hotelServiceModel));
    }


    @Test
    void delete_whenIdIsNull_shouldReturnFalse() throws Exception {
        Hotel hotel=new Hotel();
        hotel.setId(null);

        assertFalse(this.hotelService.deleteHotel(hotel.getId()));
    }


    @Test
    void delete_whenIdIsNotNull_shouldReturnFalse() throws Exception {
        Hotel hotel=new Hotel();
        hotel.setId("232");

        Mockito.when(this.hotelRepository.getById("232")).thenReturn(Optional.of(hotel));
        assertTrue(this.hotelService.deleteHotel("232"));
    }

    @Test
    void findAll_whenHaveTwo_ReturnThem(){
        Hotel hotel=new Hotel();
        hotel.setName("ev1");

        Hotel hotel2=new Hotel();
        hotel2.setName("ev2");

        List<Hotel> hotels=new ArrayList<>();
        hotels.add(hotel);
        hotels.add(hotel2);

        Mockito.when(hotelRepository.findAll()).thenReturn(hotels);

        assertEquals(hotels.size(),hotelRepository.findAll().size());
    }

    @Test
    void findAll_whenHotelsAreNull_ReturnNull(){
        List<Hotel> hotels=new ArrayList<>();

        Mockito.when(hotelRepository.findAll()).thenReturn(hotels);

        assertEquals(hotels.size(),hotelRepository.findAll().size());
    }

    @Test
    void getById_whenHotelDoesNotExist_shouldThrowHotelNotFoundException() {
        String id = null;

        Mockito.when(hotelRepository.getById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                HotelNotFoundException.class,
                () -> hotelService.findById(id));
    }

    @Test
    void getById_whenHotelDoesNotExist_shouldReturnHotelServiceModel() {
        String id = "12";

        Mockito.when(hotelRepository.getById(id))
                .thenReturn(Optional.empty());

    }

    @Test
    void findByName_whenHotelDoesNotExist_shouldThrowHotelNotFoundException() {
        String name = "name";

        Mockito.when(hotelRepository.findByName(name))
                .thenReturn(Optional.empty());

        assertThrows(
                HotelNotFoundException.class,
                () -> hotelService.findHotelByName(name));
    }




    @Test
    void findByName_whenHotelNotFound_shouldThrowHotelNotFoundException() {
        String username = "No hotel";

        Mockito.when(hotelRepository.findByName(username)).thenReturn(Optional.empty());

        assertThrows(
                HotelNotFoundException.class,
                () -> hotelService.findHotelByName(username));
    }

}
