package com.kevin.hotelbooking.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

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
    private Set<HotelImage> hotelImages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    private Set<Room> rooms = new LinkedHashSet<>();
}