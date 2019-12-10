package com.example.megaevents.data.models;

import com.example.megaevents.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="events")
public class Event extends BaseEntity {
    @Column(name="name",nullable = false)
    private String name;

    @Column(name="address")
    private String address;

    @ManyToMany
    @JoinTable(
            name = "event_hotels",
            joinColumns = @JoinColumn(name ="event_id"),
            inverseJoinColumns = @JoinColumn(name = "hotel_id"))
    private List<Hotel> hotels;

    @ManyToMany(targetEntity = UserProfile.class,cascade = CascadeType.ALL)
    private List<UserProfile> users;

    @Column(name="description",nullable = false)
    private String description;

    @Column(name="image")
    private String imageUrl;

    @Column(name="date")
    private String date;

    @Column(name="price")
    private Integer price;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Ticket> ticket;
}
