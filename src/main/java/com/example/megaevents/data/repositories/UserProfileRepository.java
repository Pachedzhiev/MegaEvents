package com.example.megaevents.data.repositories;

import com.example.megaevents.data.models.Event;
import com.example.megaevents.data.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,String> {

    @Query("select u from UserProfile u inner join u.events e where e.name in :name")
    List<UserProfile> findByEventName(String name);
}
