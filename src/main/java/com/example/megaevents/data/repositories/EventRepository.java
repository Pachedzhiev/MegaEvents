package com.example.megaevents.data.repositories;

import com.example.megaevents.data.models.Event;
import com.example.megaevents.data.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,String> {

    Optional<Event> findByName(String name);

    Optional<Event> getById(String id);

    @Query("select e from Event e inner join e.hotels h where h.name in :name")
    List<Event> findByHotelName(String name);

}
