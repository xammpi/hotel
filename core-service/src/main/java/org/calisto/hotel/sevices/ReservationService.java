package org.calisto.hotel.sevices;

import org.calisto.hotel.entity.Reservation;
import org.calisto.hotel.entity.Room;
import org.calisto.hotel.exception.ReservationAlreadyExistsException;
import org.calisto.hotel.exception.ResourceNotFoundException;
import org.calisto.hotel.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomService roomService;
    private final GuestService guestService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              RoomService roomService,
                              GuestService guestService) {
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
        this.guestService = guestService;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Page<Reservation> getAllReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable);
    }

    public Reservation findById(Integer id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id));
    }

    @Transactional
    public Reservation save(Reservation reservation) {
        if (isRoomAvailable(reservation)) {
            Reservation createdReservation = reservationRepository.save(reservation);
            createdReservation.setRoom(roomService.findById(reservation.getRoom().getId()));
            createdReservation.setGuest(guestService.findById(reservation.getGuest().getId()));
            return createdReservation;
        } else {
            Room room = roomService.findById(reservation.getRoom().getId());
            throw new ReservationAlreadyExistsException(
                    String.format("The room: %s is occupied for selected dates: Check-in date: %s, Check-out date: %s",
                            room.getRoomNumber(),
                            reservation.getCheckInDate(),
                            reservation.getCheckOutDate())
            );
        }
    }

    @Transactional
    public Reservation update(Integer id, Reservation updatedReservation) {
        if (isRoomAvailable(updatedReservation)) {
            Reservation existingReservation = findById(id);
            existingReservation.setRoom(updatedReservation.getRoom());
            existingReservation.setGuest(updatedReservation.getGuest());
            existingReservation.setCheckInDate(updatedReservation.getCheckInDate());
            existingReservation.setCheckOutDate(updatedReservation.getCheckOutDate());
            return reservationRepository.save(updatedReservation);
        } else {
            Room room = roomService.findById(updatedReservation.getRoom().getId());
            throw new ReservationAlreadyExistsException(
                    String.format("The room: %s is occupied for selected dates: Check-in date:%s, Check-out date: %s",
                            room.getRoomNumber(),
                            updatedReservation.getCheckInDate(),
                            updatedReservation.getCheckOutDate())
            );
        }
    }

    @Transactional
    public void deleteById(Integer id) {
        reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id));
        reservationRepository.deleteById(id);
    }

    public boolean isRoomAvailable(Reservation reservation) {
        List<Reservation> overlappingReservations = reservationRepository
                .findByRoomAndCheckOutDateGreaterThanEqualAndCheckInDateLessThanEqual(
                        reservation.getRoom(), reservation.getCheckInDate()
                        , reservation.getCheckOutDate());
        return overlappingReservations.isEmpty();
    }
}
