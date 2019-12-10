package com.example.megaevents.data.models;

import com.example.megaevents.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="hotels")
public class Hotel extends BaseEntity {
    @Column(name="name",nullable = false)
    private String name;

    @Column(name="address",nullable = false)
    private String address;

    @ManyToMany(targetEntity = Event.class, fetch = FetchType.EAGER)
    private List<Event> events;

    @Column(name="description",nullable = false)
    private String description;

    @Column(name="singleRoom")
    private Integer singleRoom;

    @Column(name="doubleRoom")
    private Integer doubleRoom;

    @Column(name="roomForThree")
    private Integer roomForThree;

    @Column(name="roomForFour")
    private Integer roomForFour;

    @Column(name="image")
    private String imageUrl;

    @Column(name="price")
    private Integer price;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Ticket> ticket;

}
