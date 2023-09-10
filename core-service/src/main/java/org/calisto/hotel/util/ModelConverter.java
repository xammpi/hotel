package org.calisto.hotel.util;

import org.calisto.hotel.dto.GuestDTO;
import org.calisto.hotel.dto.ReservationDTO;
import org.calisto.hotel.dto.RoomDTO;
import org.calisto.hotel.entity.Guest;
import org.calisto.hotel.entity.Reservation;
import org.calisto.hotel.entity.Room;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

//@UtilityClass
@Component
public class ModelConverter {
    private static final ModelMapper modelMapper = new ModelMapper();

    public <D, S> D convert(S source, Class<D> destinationType) {
        D dto = modelMapper.map(source, destinationType);
        return dto;
    }

    public ReservationDTO convertReservationToDTO(Reservation reservation) {
        ReservationDTO reservationDTO = modelMapper.map(reservation, ReservationDTO.class);
        reservationDTO.setGuestDto(convertGuestToDTO(reservation.getGuest()));
        reservationDTO.setRoomDto(convertRoomToDTO(reservation.getRoom()));
        return reservationDTO;
    }

    public List<ReservationDTO> convertReservationToDTO(List<Reservation> reservations) {
        return reservations.stream()
                .map(this::convertReservationToDTO)
                .collect(toList());
    }

    public Reservation convertToReservation(ReservationDTO reservationDTO) {
        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        reservation.setGuest(convertToGuest(reservationDTO.getGuestDto()));
        reservation.setRoom(convertToRoom(reservationDTO.getRoomDto()));
        return reservation;
    }

    public GuestDTO convertGuestToDTO(Guest guest) {
        return modelMapper.map(guest, GuestDTO.class);
    }

    public List<GuestDTO> convertGuestToDTO(List<Guest> guests) {
        return guests.stream()
                .map(this::convertGuestToDTO)
                .collect(toList());
    }

    public Guest convertToGuest(GuestDTO guestDTO) {
        return modelMapper.map(guestDTO, Guest.class);
    }

    public RoomDTO convertRoomToDTO(Room room) {
        return modelMapper.map(room, RoomDTO.class);
    }

    public List<RoomDTO> convertRoomToDTO(List<Room> rooms) {
        return rooms.stream()
                .map(this::convertRoomToDTO)
                .collect(toList());
    }

    public Room convertToRoom(RoomDTO roomDTO) {
        return modelMapper.map(roomDTO, Room.class);
    }
}
