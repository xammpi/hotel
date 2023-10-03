package org.calisto.hotel.util.converters;

import org.calisto.hotel.dto.GuestDTO;
import org.calisto.hotel.dto.ReservationDTO;
import org.calisto.hotel.dto.RoomDTO;
import org.calisto.hotel.entity.Guest;
import org.calisto.hotel.entity.Reservation;
import org.calisto.hotel.entity.Room;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservationConverter extends BaseConverter<ReservationDTO, Reservation> {
    private final GuestConverter guestConverter;
    private final RoomConverter roomConverter;

    public ReservationConverter(ModelMapper modelMapper,
                                GuestConverter guestConverter,
                                RoomConverter roomConverter) {
        super(modelMapper);
        this.guestConverter = guestConverter;
        this.roomConverter = roomConverter;
    }

    @Override
    protected Class<Reservation> entityClass() {
        return Reservation.class;
    }

    @Override
    protected Class<ReservationDTO> dtoClass() {
        return ReservationDTO.class;
    }

    @Override
    public ReservationDTO convertToDTO(Reservation entity) {
        ReservationDTO reservationDTO = super.convertToDTO(entity);
        RoomDTO roomDTO = roomConverter.convertToDTO(entity.getRoom());
        GuestDTO guestDTO = guestConverter.convertToDTO(entity.getGuest());
        reservationDTO.setRoomDto(roomDTO);
        reservationDTO.setGuestDto(guestDTO);
        return reservationDTO;
    }

    @Override
    public List<ReservationDTO> convertToDTOList(Collection<? extends Reservation> entityList) {
        return entityList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Reservation convertToEntity(ReservationDTO dto) {
        Reservation reservation = super.convertToEntity(dto);
        Room room = roomConverter.convertToEntity(dto.getRoomDto());
        Guest guest = guestConverter.convertToEntity(dto.getGuestDto());
        reservation.setRoom(room);
        reservation.setGuest(guest);
        return reservation;
    }
}
