package org.calisto.hotel.util.converters;

import org.calisto.hotel.dto.RoomDTO;
import org.calisto.hotel.entity.Room;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoomConverter extends BaseConverter<RoomDTO, Room> {

    public RoomConverter(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
