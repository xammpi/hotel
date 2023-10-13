package org.calisto.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "guest")
public class Guest extends BaseEntity {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @JsonIgnore
    @OneToMany(mappedBy = "guest", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Reservation> reservations;
}
