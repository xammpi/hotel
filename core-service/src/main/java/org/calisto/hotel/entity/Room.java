package org.calisto.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "room")
public class Room extends BaseEntity {
    @Column(name = "room_number")
    private Integer roomNumber;
    @Column(name = "capacity")
    private int capacity;
    @JsonIgnore
    @OneToMany(mappedBy = "room", cascade = {CascadeType.ALL})
    private List<Reservation> reservations;
}
