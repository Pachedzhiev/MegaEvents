package com.example.megaevents.data.models;

import com.example.megaevents.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="tickets")
public class Ticket extends BaseEntity {
    @ManyToOne(targetEntity = UserProfile.class)
    @JoinColumn(name="user_id",nullable = false)
    private UserProfile user;

    @ManyToOne(targetEntity = Event.class)
    @JoinColumn(name="event_id",nullable = false)
    private Event event;

    @ManyToOne(targetEntity = Hotel.class)
    @JoinColumn(name="hotel_id")
    private Hotel hotel;

    @Column(name="count")
    private Integer count;
}
