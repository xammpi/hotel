package org.calisto.hotel.repositories;

import org.calisto.hotel.entity.Reservation;
import org.calisto.hotel.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Page<Reservation> findAll(Pageable pageable);

    List<Reservation> findByRoomAndCheckOutDateGreaterThanEqualAndCheckInDateLessThanEqual(
            Room room, LocalDate checkOutDate, LocalDate checkInDate);
}
