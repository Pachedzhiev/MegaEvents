package com.example.megaevents.services.services;

import com.example.megaevents.base.TestBase;
import com.example.megaevents.data.models.*;
import com.example.megaevents.data.repositories.*;
import com.example.megaevents.errors.HotelNotFoundException;
import com.example.megaevents.services.models.HotelServiceModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.constraints.AssertTrue;
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
    TicketRepository ticketRepository;

    @MockBean
    UserProfileRepository userProfileRepository;

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
        HotelServiceModel hotelServiceModel = new HotelServiceModel();
        hotelServiceModel.setName("hotel");
        assertTrue(this.hotelService.save(hotelServiceModel));
    }


    @Test
    void delete_whenIdIsNull_shouldReturnFalse() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setId(null);

        assertFalse(this.hotelService.deleteHotel(hotel.getId()));
    }


    @Test
    void delete_whenIdIsNotNull_shouldReturnFalse() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setId("232");

        Mockito.when(this.hotelRepository.getById("232")).thenReturn(Optional.of(hotel));
        assertTrue(this.hotelService.deleteHotel("232"));
    }

    @Test
    void findAll_whenHaveTwo_ReturnThem() {
        Hotel hotel = new Hotel();
        hotel.setName("ev1");

        Hotel hotel2 = new Hotel();
        hotel2.setName("ev2");

        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);
        hotels.add(hotel2);

        Mockito.when(hotelRepository.findAll()).thenReturn(hotels);

        assertEquals(hotels.size(), hotelRepository.findAll().size());
    }

    @Test
    void findAll_whenHotelsAreNull_ReturnNull() {
        List<Hotel> hotels = new ArrayList<>();

        Mockito.when(hotelRepository.findAll()).thenReturn(hotels);

        assertEquals(hotels.size(), hotelRepository.findAll().size());
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


    @Test
    void addHotel_whenHotelIsNull_shouldReturnFalse() throws Exception {
        assertFalse(this.hotelService.addHotel("asdas", null));
    }

    @Test
    void addHotel_whenEventIsNull_shouldReturnFalse() throws Exception {
        assertFalse(this.hotelService.addHotel(null, "asdas"));
    }


    @Test
    void addHotel_whenWeHaveEventAndHotel_shouldReturnTrue() throws Exception {
        String eventName = "event";
        Event event = new Event();
        event.setName(eventName);

        String hotelName = "hotel";
        Hotel hotel = new Hotel();
        hotel.setName(hotelName);

        Hotel hotel2 = new Hotel();
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel2);
        event.setHotels(hotels);

        List<Event> events = new ArrayList<>();

        Event event1 = new Event();
        Event event2 = new Event();
        events.add(event1);
        events.add(event2);
        hotel.setEvents(events);

        Mockito.when(this.eventRepository.findByName("event")).thenReturn(Optional.of(event));
        Mockito.when(this.hotelRepository.findByName("hotel")).thenReturn(Optional.of(hotel));
        Mockito.when(this.eventRepository.save(event)).thenReturn(event);

        assertTrue(this.hotelService.addHotel(eventName, hotelName));
    }

    @Test
    void reserve_whenUsernameIsNull_shouldReturnFalse() throws Exception {
        assertFalse(this.hotelService.reserve("name", null, 2, 2, 2, 2));
    }

    @Test
    void reserve_whenIdIsNull_shouldReturnFalse() throws Exception {
        assertFalse(this.hotelService.reserve(null, "id", 2, 2, 2, 2));
    }

    @Test
    void reserve_whenHotelNotFound_shouldThrowHotelException() {

        String name = "No event";

        User user = new User();
        user.setUsername(name);

        Mockito.when(userRepository.findUserByUsername(name)).thenReturn(Optional.of(user));
        Mockito.when(hotelRepository.getById(name)).thenReturn(Optional.empty());

        assertThrows(
                HotelNotFoundException.class,
                () -> hotelService.reserve(name, name, 2, 2, 2, 2));
    }

    @Test
    void reserve_whenUserNotFound_shouldThrowHotelException() {

        String name = "No user";
        Hotel hotel = new Hotel();
        hotel.setId("23");


        Mockito.when(userRepository.findUserByUsername(name)).thenReturn(Optional.empty());
        Mockito.when(hotelRepository.getById("23")).thenReturn(Optional.of(hotel));

        assertThrows(
                Exception.class,
                () -> hotelService.reserve(name, "23", 2, 2, 2, 2));

    }

    @Test
    void reserve_whenNothingIsNull_shouldReturnTrue() throws Exception {
        User user = new User();
        user.setUsername("user");


        UserProfile userProfile = new UserProfile();
        user.setUserProfile(userProfile);

        Event event3 = new Event();
        Event event2 = new Event();
        List<Event> eventsa = new ArrayList<>();
        eventsa.add(event2);
        eventsa.add(event3);

        userProfile.setEvents(eventsa);

        List<UserProfile> users = new ArrayList<>();
        users.add(userProfile);

        Event event = new Event();
        event.setName("event");
        event.setId("sd");
        event.setPrice(20);

        event.setUsers(users);

        Integer count = 5;

        Ticket ticket = new Ticket();

        Hotel hotel=new Hotel();
        hotel.setPrice(2);
        hotel.setRoomForFour(2);
        hotel.setRoomForThree(2);
        hotel.setDoubleRoom(2);
        hotel.setSingleRoom(2);
        hotel.setId("23");


        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
        Mockito.when(hotelRepository.getById(event.getId())).thenReturn(Optional.of(hotel));
        Mockito.when(ticketRepository.save(ticket)).thenReturn(ticket);
        Mockito.when(hotelRepository.save(hotel)).thenReturn(hotel);



        assertTrue(hotelService.reserve("user", "sd", 2,2,2,2));

    }


}
