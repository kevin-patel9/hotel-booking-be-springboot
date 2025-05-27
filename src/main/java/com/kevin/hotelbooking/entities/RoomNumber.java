package com.kevin.hotelbooking.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "room_numbers")
public class RoomNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "number")
    private Integer number;

    @OneToMany(mappedBy = "room")
    private Set<BookedRoom> bookedRooms = new LinkedHashSet<>();

}