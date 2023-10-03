package org.calisto.hotel.util.converters;

import org.calisto.hotel.dto.GuestDTO;
import org.calisto.hotel.entity.Guest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Component
public class GuestConverter extends BaseConverter<GuestDTO, Guest> {

    public GuestConverter(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<GuestDTO> dtoClass() {
        return GuestDTO.class;
    }

    @Override
    protected Class<Guest> entityClass() {
        return Guest.class;
    }

    @Override
    public Guest convertToEntity(GuestDTO dto) {
        return super.convertToEntity(dto);
    }

    @Override
    public GuestDTO convertToDTO(Guest entity) {
        return super.convertToDTO(entity);
    }

    @Override
    public List<GuestDTO> convertToDTOList(Collection<? extends Guest> entityList) {
        return super.convertToDTOList(entityList);
    }

    @Override
    public List<Guest> convertToEntityList(Collection<? extends GuestDTO> dtoList) {
        return super.convertToEntityList(dtoList);
    }

    @Override
    public Function<GuestDTO, Guest> convertToEntity(Class<Guest> entityClass) {
        return super.convertToEntity(entityClass);
    }

    @Override
    public Function<Guest, GuestDTO> convertToDTO(Class<GuestDTO> dtoClass) {
        return super.convertToDTO(dtoClass);
    }
}
