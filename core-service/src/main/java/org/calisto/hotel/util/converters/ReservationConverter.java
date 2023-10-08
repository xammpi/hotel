package org.calisto.hotel.util.converters;

import org.calisto.hotel.dto.ReservationDTO;
import org.calisto.hotel.entity.Reservation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class ReservationConverter extends BaseConverter<ReservationDTO, Reservation> {
    private final GuestConverter guestConverter;
    private final RoomConverter roomConverter;

    @Autowired
    public ReservationConverter(ModelMapper modelMapper,
                                GuestConverter guestConverter,
                                RoomConverter roomConverter) {
        super(modelMapper);
        this.guestConverter = guestConverter;
        this.roomConverter = roomConverter;
    }

    @Override
    public Reservation convertToEntity(ReservationDTO dto) {
        Reservation reservation = super.convertToEntity(dto);
        reservation.setGuest(guestConverter.convertToEntity(dto.getGuestDto()));
        reservation.setRoom(roomConverter.convertToEntity(dto.getRoomDto()));
        return reservation;
    }

    @Override
    public ReservationDTO convertToDTO(Reservation entity) {
        ReservationDTO reservationDTO = super.convertToDTO(entity);
        reservationDTO.setGuestDto(guestConverter.convertToDTO(entity.getGuest()));
        reservationDTO.setRoomDto(roomConverter.convertToDTO(entity.getRoom()));
        return reservationDTO;
    }

    @Override
    public List<ReservationDTO> convertToDTOList(Collection<? extends Reservation> entityList) {
        return entityList.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<Reservation> convertToEntityList(Collection<? extends ReservationDTO> dtoList) {
        return dtoList.stream()
                .map(this::convertToEntity)
                .toList();
    }
}
