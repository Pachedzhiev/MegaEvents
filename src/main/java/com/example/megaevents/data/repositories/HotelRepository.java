package com.example.megaevents.data.repositories;

import com.example.megaevents.data.models.Event;
import com.example.megaevents.data.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,String> {

    Optional<Hotel> findByName(String name);

    @Query("select h from Hotel h \n" +
            "inner join h.events e\n" +
            "where e.name in :name")
    List<Hotel> findAllByEvents(String name);



}
