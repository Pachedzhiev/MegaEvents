package com.example.megaevents.data.repositories;

import com.example.megaevents.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findUserByUsername(String username);

    Optional<User> getById(String id);
}
