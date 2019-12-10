package com.example.megaevents.data.repositories;

import com.example.megaevents.data.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,String> {

    Optional<Event> findByName(String name);

    Event getById(String id);

}
