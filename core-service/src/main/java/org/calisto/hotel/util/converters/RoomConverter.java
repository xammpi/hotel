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

    @Override
    public Room convertToEntity(RoomDTO dto) {
        return super.convertToEntity(dto);
    }

    @Override
    public RoomDTO convertToDTO(Room entity) {
        return super.convertToDTO(entity);
    }

    @Override
    public List<RoomDTO> convertToDTOList(Collection<? extends Room> entityList) {
        return super.convertToDTOList(entityList);
    }

    @Override
    public List<Room> convertToEntityList(Collection<? extends RoomDTO> dtoList) {
        return super.convertToEntityList(dtoList);
    }

    @Override
    public Function<RoomDTO, Room> convertToEntity(Class<Room> entityClass) {
        return super.convertToEntity(entityClass);
    }

    @Override
    public Function<Room, RoomDTO> convertToDTO(Class<RoomDTO> dtoClass) {
        return super.convertToDTO(dtoClass);
    }
}
