package org.calisto.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
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
