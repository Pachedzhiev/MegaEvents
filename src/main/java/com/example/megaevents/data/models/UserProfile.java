package com.example.megaevents.data.models;

import com.example.megaevents.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="user_profiles")
public class UserProfile extends BaseEntity {

    @Column(name="number",nullable = false)
    private String number;

    @Column(name="fullName",nullable = false)
    private String fullName;

    @OneToOne(targetEntity =User.class, mappedBy = "userProfile")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "user_events",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> events;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Ticket> ticket;


}
