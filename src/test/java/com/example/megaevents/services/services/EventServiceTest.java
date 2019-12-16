package com.example.megaevents.services.services;

import com.example.megaevents.base.TestBase;
import com.example.megaevents.data.models.Event;
import com.example.megaevents.data.models.User;
import com.example.megaevents.data.models.UserProfile;
import com.example.megaevents.data.models.base.BaseEntity;
import com.example.megaevents.data.repositories.EventRepository;
import com.example.megaevents.data.repositories.UserRepository;
import com.example.megaevents.errors.EventNotFoundException;
import com.example.megaevents.services.models.EventServiceModel;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventServiceTest extends TestBase {
    @MockBean
    EventRepository eventRepository;

    @MockBean
    UserRepository userRepository;

    @Autowired
    EventService eventService;


    @Test
    void save_whenEventServiceModelIsNull_shouldReturnFalse() {

        assertFalse(this.eventService.save(null));
    }

    @Test
    void save_whenEventServiceModelIsNotNull_shouldReturnFalse() {
        EventServiceModel eventServiceModel=new EventServiceModel();
        eventServiceModel.setName("event");
        assertTrue(this.eventService.save(eventServiceModel));
    }
    @Test
    void getById_whenEventDoesNotExist_shouldThrowEventNotFoundException() {
        String id = null;

        Mockito.when(eventRepository.getById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                EventNotFoundException.class,
                () -> eventService.findEventByName(id));
    }

    @Test
    void getById_whenEventDoesNotExist_shouldRetrunEventServiceModel() {
        String id = "12";

        Mockito.when(eventRepository.getById(id))
                .thenReturn(Optional.empty());

    }

    @Test
    void findByName_whenEventDoesNotExist_shouldThrowEventNotFoundException() {
        String name = "name";

        Mockito.when(eventRepository.findByName(name))
                .thenReturn(Optional.empty());

        assertThrows(
                EventNotFoundException.class,
                () -> eventService.findEventByName(name));
    }




    @Test
    void findByName_whenEventNotFound_shouldThrowEventNotFoundException() {
        String username = "No event";

        Mockito.when(eventRepository.findByName(username)).thenReturn(Optional.empty());

        assertThrows(
                EventNotFoundException.class,
                () -> eventService.findEventByName(username));
    }


    @Test
    void reserve_whenUserNotFound_shouldThrowException() {

        String username = "No user";

        Mockito.when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());

        assertThrows(
                Exception.class,
                () -> eventService.reserve(username,"rewrw",2));
    }

    @Test
    void reserve_whenEventNotFound_shouldThrowEventNotFoundException() {

        String name = "No event";

        User user=new User();
        user.setUsername("mal");


        Mockito.when(eventRepository.getById(name)).thenReturn(Optional.empty());

        assertThrows(
                Exception.class,
                () -> eventService.reserve("mal",name,2));
    }


    @Test
    void delete_whenIdIsNull_shouldReturnFalse(){
        Event event=new Event();
        event.setId(null);

        assertFalse(this.eventService.deleteEvent(event.getId()));
    }


    @Test
    void delete_whenIdIsNotNull_shouldReturnFalse(){
        Event event=new Event();
        event.setId("232");

        Mockito.when(this.eventRepository.getById("232")).thenReturn(Optional.of(event));
        assertTrue(this.eventService.deleteEvent("232"));
    }


    @Test
    void findAll_whenHaveTwo_ReturnThem(){
        Event event=new Event();
        event.setName("ev1");

        Event event2=new Event();
        event2.setName("ev2");

        List<Event> events=new ArrayList<>();
        events.add(event);
        events.add(event2);

        Mockito.when(eventRepository.findAll()).thenReturn(events);

        assertEquals(events.size(),eventRepository.findAll().size());
    }

    @Test
    void findAll_whenEventsAreNull_ReturnNull(){
        List<Event> events=new ArrayList<>();

        Mockito.when(eventRepository.findAll()).thenReturn(events);

        assertEquals(events.size(),eventRepository.findAll().size());
    }







//    @Test
//    void reserve_whenEventFound_shouldMakeTicket() {
//
//
//        User user=new User();
//        user.setUsername("user");
//        User user2=new User();
//        user.setUsername("user2");
//
//        UserProfile userProfile= new UserProfile();
//        userProfile.setUser(user);
//        UserProfile userProfile2= new UserProfile();
//        userProfile.setUser(user2);
//
//        List<UserProfile> users=new ArrayList<>();
//        users.add(userProfile);
//        users.add(userProfile2);
//
//
//        Event event= new Event();
//        event.setName("event");
//        event.setId("sd");
//
//        event.getUsers().add(userProfile);
//
//        event.setUsers(users);
//
//
//
//        Mockito.when(eventRepository.findByName(event.getName())).thenReturn(Optional.empty());
//
//        assertThrows(
//                Exception.class,
//                () -> eventService.reserve("mal",event.getId(),2));
//    }









}
