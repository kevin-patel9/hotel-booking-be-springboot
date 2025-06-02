package com.kevin.hotelbooking.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "max_people")
    private Byte maxPeople;

    @Column(name = "`desc`")
    private String desc;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JsonBackReference
    private Hotel hotel;

    @OneToMany(mappedBy = "room")
    @JsonManagedReference
    private Set<RoomNumber> roomNumbers = new LinkedHashSet<>();
}