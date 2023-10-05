package org.calisto.hotel.sevices.reservation;

import org.calisto.hotel.dto.ReservationDTO;
import org.calisto.hotel.entity.Reservation;
import org.calisto.hotel.exception.ReservationAlreadyExistsException;
import org.calisto.hotel.exception.ResourceNotFoundException;
import org.calisto.hotel.repositories.ReservationRepository;
import org.calisto.hotel.util.converters.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final BaseConverter reservationConverter = BaseConverter.create(ReservationDTO.class, Reservation.class);
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<ReservationDTO> findAll() {
        List<Reservation> reservationList = reservationRepository.findAll();
        return reservationConverter.convertToDTOList(reservationList);
    }

    @Override
    public Page<ReservationDTO> findAll(Pageable pageable) {
        return reservationRepository.findAll(pageable)
                .map(reservationConverter::convertToDTO);
    }

    @Override
    public ReservationDTO findById(Integer id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id));
        System.out.println(reservation.getRoom());
        return reservationConverter.convertToDTO(reservation);
    }

    @Transactional
    @Override
    public ReservationDTO save(ReservationDTO reservationDTO) {
        System.out.println(reservationDTO);
        Reservation reservation = reservationConverter.convertToEntity(reservationDTO);
        if (isRoomAvailable(reservation)) {
            Reservation createdReservation = reservationRepository.save(reservation);
            return reservationConverter.convertToDTO(createdReservation);
        } else {
            throw new ReservationAlreadyExistsException(
                    String.format("The room: %s is occupied for selected dates: Check-in date: %s, Check-out date: %s",
                            reservation.getRoom().getRoomNumber(),
                            reservation.getCheckInDate(),
                            reservation.getCheckOutDate())
            );
        }
    }

    @Transactional
    @Override
    public ReservationDTO update(Integer id, ReservationDTO updatedReservation) {
        Reservation reservation = reservationConverter.convertToEntity(updatedReservation);
        if (isRoomAvailable(reservation)) {
            reservation.setCheckInDate(updatedReservation.getCheckInDate());
            reservation.setCheckOutDate(updatedReservation.getCheckOutDate());
            Reservation savedReservation = reservationRepository.save(reservation);
            return reservationConverter.convertToDTO(savedReservation);
        } else {
            throw new ReservationAlreadyExistsException(
                    String.format("The room: %s is occupied for selected dates: Check-in date:%s, Check-out date: %s",
                            reservation.getRoom().getRoomNumber(),
                            updatedReservation.getCheckInDate(),
                            updatedReservation.getCheckOutDate())
            );
        }
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id));
        reservationRepository.deleteById(id);
    }

    public boolean isRoomAvailable(Reservation reservation) {
        System.out.println(reservation);
        List<Reservation> overlappingReservations = reservationRepository
                .findByRoomAndCheckOutDateGreaterThanEqualAndCheckInDateLessThanEqual(
                        reservation.getRoom(), reservation.getCheckInDate()
                        , reservation.getCheckOutDate());
        return overlappingReservations.isEmpty();
    }
}
