package org.calisto.hotel.util.converters;

import org.calisto.hotel.dto.RoomDTO;
import org.calisto.hotel.entity.Room;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Component
public class RoomConverter extends BaseConverter<RoomDTO, Room> {

    public RoomConverter(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<RoomDTO> dtoClass() {
        return RoomDTO.class;
    }

    @Override
    protected Class<Room> entityClass() {
        return Room.class;
    }
}
