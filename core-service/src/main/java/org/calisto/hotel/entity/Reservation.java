package org.calisto.hotel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "reservation")
public class Reservation extends BaseEntity{
    @Column(name = "check_in_date")
    private LocalDate checkInDate;
    @Column(name = "check_out_date")
    private LocalDate checkOutDate;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;
}
