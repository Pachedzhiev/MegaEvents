package com.example.megaevents.services.services;

import com.example.megaevents.base.TestBase;
import com.example.megaevents.data.models.Event;
import com.example.megaevents.data.models.User;
import com.example.megaevents.data.models.UserProfile;
import com.example.megaevents.data.repositories.EventRepository;
import com.example.megaevents.data.repositories.UserRepository;
import com.example.megaevents.errors.EventNotFoundException;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventServiceTest extends TestBase {
    @MockBean
    EventRepository eventRepository;

    @MockBean
    UserRepository userRepository;

    @Autowired
    EventService eventService;


    @Test
    void getByName_whenEventDoesNotExist_shouldThrowEventNotFoundException() {
        String heroName = "Hero Name";

        Mockito.when(eventRepository.findByName(heroName))
                .thenReturn(Optional.empty());

        assertThrows(
                EventNotFoundException.class,
                () -> eventService.findEventByName(heroName));
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
//
//        event.getUsers().add(userProfile);
//
//        event.setUsers(users);
//
//
//
//        Mockito.when(eventRepository.getById(name)).thenReturn(Optional.empty());
//
//        assertThrows(
//                Exception.class,
//                () -> eventService.reserve("mal",name,2));
//    }









}
