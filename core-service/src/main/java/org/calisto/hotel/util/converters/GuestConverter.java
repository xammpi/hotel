package org.calisto.hotel.util.converters;

import org.calisto.hotel.dto.GuestDTO;
import org.calisto.hotel.entity.Guest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GuestConverter extends BaseConverter<GuestDTO, Guest> {

    public GuestConverter(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
