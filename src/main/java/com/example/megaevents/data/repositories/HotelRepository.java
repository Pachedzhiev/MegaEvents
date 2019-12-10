package com.example.megaevents.data.repositories;

import com.example.megaevents.data.models.Event;
import com.example.megaevents.data.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,String> {

    Optional<Hotel> findByName(String name);


}
