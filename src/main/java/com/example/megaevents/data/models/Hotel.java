package com.example.megaevents.data.models;

import com.example.megaevents.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.List;

import static com.example.megaevents.constants.ConstantError.ERROR_ADDRESS;
import static com.example.megaevents.constants.ConstantError.ERROR_DESCRIPTION;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="hotels")
public class Hotel extends BaseEntity {
    @Column(name="name",nullable = false)
    private String name;

    @Column(name="address",nullable = false)
    @Length(min = 6,message = ERROR_ADDRESS)
    private String address;

    @ManyToMany(targetEntity = Event.class, fetch = FetchType.EAGER)
    private List<Event> events;

    @Column(name="description",nullable = false)
    @Length(min = 10,message = ERROR_DESCRIPTION)
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
