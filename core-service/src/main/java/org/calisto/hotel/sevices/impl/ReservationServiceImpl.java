package org.calisto.hotel.sevices.impl;

import org.calisto.hotel.dto.ReservationDTO;
import org.calisto.hotel.entity.Reservation;
import org.calisto.hotel.entity.Room;
import org.calisto.hotel.exception.ResourceConflictException;
import org.calisto.hotel.exception.ResourceNotFoundException;
import org.calisto.hotel.repositories.ReservationRepository;
import org.calisto.hotel.sevices.ReservationService;
import org.calisto.hotel.utils.converters.ReservationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationConverter converter;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(ReservationConverter converter,
                                  ReservationRepository reservationRepository) {
        this.converter = converter;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<ReservationDTO> findAll() {
        List<Reservation> reservationList = reservationRepository.findAll();
        return converter.convertToDTOList(reservationList);
    }

    @Override
    public Page<ReservationDTO> findAll(Pageable pageable) {
        return reservationRepository.findAll(pageable)
                .map(converter::convertToDTO);
    }

    @Override
    public ReservationDTO findById(Integer id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return converter.convertToDTO(reservation);
    }

    @Transactional
    @Override
    public ReservationDTO save(ReservationDTO reservationDTO) {
        Reservation reservation = converter.convertToEntity(reservationDTO);
        if (isReservationAvailable(reservation)) {
            throw new ResourceConflictException();
        }
        Reservation savedReservation = reservationRepository.save(reservation);
        return converter.convertToDTO(savedReservation);
    }

    @Transactional
    @Override
    public ReservationDTO update(Integer id, ReservationDTO updatedReservation) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        if (isReservationAvailable(reservation)) {
            throw new ResourceConflictException();
        }
        return converter.convertToDTO(reservation);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        reservationRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> findByRoomAndCheckOutDateGreaterThanEqualAndCheckInDateLessThanEqual(Room room,
                                                                                                  LocalDate checkOutDate,
                                                                                                  LocalDate checkInDate) {
        return reservationRepository
                .findByRoomAndCheckOutDateGreaterThanEqualAndCheckInDateLessThanEqual(
                        room, checkInDate, checkOutDate);
    }

    private boolean isReservationAvailable(Reservation reservation) {
        List<Reservation> overlappingReservations = findByRoomAndCheckOutDateGreaterThanEqualAndCheckInDateLessThanEqual(
                reservation.getRoom(),
                reservation.getCheckInDate()
                , reservation.getCheckOutDate());
        return !overlappingReservations.isEmpty();
    }
}
