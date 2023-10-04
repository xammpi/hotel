package org.calisto.hotel.sevices.impl;

import org.calisto.hotel.dto.RoomDTO;
import org.calisto.hotel.entity.Room;
import org.calisto.hotel.exception.ResourceNotFoundException;
import org.calisto.hotel.exception.RoomAlreadyExistsException;
import org.calisto.hotel.repositories.RoomRepository;
import org.calisto.hotel.sevices.RoomService;
import org.calisto.hotel.util.converters.BaseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    private final BaseConverter<RoomDTO, Room> converter;
    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(BaseConverter<RoomDTO, Room> converter, RoomRepository roomRepository) {
        this.converter = converter;
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomDTO> findAll() {
        List<Room> roomList = roomRepository.findAll();
        return converter.convertToDTOList(roomList);
    }

    @Override
    public Page<RoomDTO> findAll(Pageable pageable) {
        return roomRepository.findAll(pageable).map(converter::convertToDTO);
    }

    @Override
    public RoomDTO findById(Integer id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room", "id", id));
        return converter.convertToDTO(room);
    }

    @Transactional
    @Override
    public RoomDTO save(RoomDTO roomDTO) {
        if (isRoomExist(roomDTO.getRoomNumber())) {
            throw new RoomAlreadyExistsException(String.format("The room with number: %s is already exist",
                    roomDTO.getRoomNumber()));
        }
        Room entity = converter.convertToEntity(roomDTO);
        Room savedGuest = roomRepository.save(entity);
        return converter.convertToDTO(savedGuest);
    }

    @Transactional
    @Override
    public RoomDTO update(Integer id, RoomDTO updatedRoom) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", id));
        ;
        room.setRoomNumber(updatedRoom.getRoomNumber());
        room.setCapacity(updatedRoom.getCapacity());
        Room savedRoom = roomRepository.save(room);
        return converter.convertToDTO(savedRoom);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room", "id", id));
        roomRepository.deleteById(id);
    }

    @Override
    public RoomDTO findByRoomNumber(Integer roomNumber) {
        Room room = roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Room", "room number", roomNumber));
        return converter.convertToDTO(room);
    }

    private boolean isRoomExist(Integer roomNumber) {
        Optional<Room> existingRoom = roomRepository.findByRoomNumber(roomNumber);
        return existingRoom.isPresent();
    }
}
