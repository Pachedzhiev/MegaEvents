package com.example.megaevents.data.repositories;

import com.example.megaevents.data.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,String> {



    @Query("select t from Ticket t join t.user u join u.user us where us.username=:username")
    List<Ticket> findTicketByUsername(String username);
}
