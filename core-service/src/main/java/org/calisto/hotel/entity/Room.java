package org.calisto.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "room_number")
    private Integer roomNumber;
    @Column(name = "capacity")
    private int capacity;
    @JsonIgnore
    @OneToMany(mappedBy = "room", cascade = {CascadeType.ALL})
    private List<Reservation> reservations;
}
