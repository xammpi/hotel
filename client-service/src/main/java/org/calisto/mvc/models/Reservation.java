package org.calisto.mvc.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation implements Serializable {

    private Integer id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Room room;
    private Guest guest;
}
