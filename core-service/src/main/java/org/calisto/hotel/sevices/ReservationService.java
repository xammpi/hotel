package org.calisto.hotel.sevices;

import org.calisto.hotel.dto.ReservationDTO;
import org.calisto.hotel.entity.Reservation;
import org.calisto.hotel.entity.Room;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService extends CrudService<ReservationDTO>{

    List<Reservation> findByRoomAndCheckOutDateGreaterThanEqualAndCheckInDateLessThanEqual(
            Room room, LocalDate checkOutDate, LocalDate checkInDate);

}
