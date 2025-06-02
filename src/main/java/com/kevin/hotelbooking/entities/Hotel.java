package com.kevin.hotelbooking.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "distance")
    private String distance;

    @Column(name = "`desc`")
    private String desc;

    @Column(name = "rating")
    private Byte rating;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "featured")
    private Boolean featured;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<HotelImage> photo = new LinkedHashSet<>();

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Room> rooms = new LinkedHashSet<>();
}