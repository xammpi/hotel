package org.calisto.hotel.sevices.impl;

import org.calisto.hotel.dto.RoomDTO;
import org.calisto.hotel.entity.Room;
import org.calisto.hotel.exception.ResourceConflictException;
import org.calisto.hotel.exception.ResourceNotFoundException;
import org.calisto.hotel.repositories.RoomRepository;
import org.calisto.hotel.sevices.RoomService;
import org.calisto.hotel.utils.converters.RoomConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomConverter converter;
    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomConverter converter, RoomRepository roomRepository) {
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
        Room room = roomRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return converter.convertToDTO(room);
    }

    @Transactional
    @Override
    public RoomDTO save(RoomDTO roomDTO) {
        if (isRoomNumberExist(roomDTO.getRoomNumber())) {
            throw new ResourceConflictException();
        }
        Room entity = converter.convertToEntity(roomDTO);
        Room savedGuest = roomRepository.save(entity);
        return converter.convertToDTO(savedGuest);
    }

    @Transactional
    @Override
    public RoomDTO update(Integer id, RoomDTO updatedRoom) {
        Room room = roomRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        room.setRoomNumber(updatedRoom.getRoomNumber());
        room.setCapacity(updatedRoom.getCapacity());
        Room savedRoom = roomRepository.save(room);
        return converter.convertToDTO(savedRoom);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        roomRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        roomRepository.deleteById(id);
    }

    @Override
    public RoomDTO findByRoomNumber(Integer roomNumber) {
        Room room = roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(ResourceNotFoundException::new);
        return converter.convertToDTO(room);
    }

    @Override
    public boolean isRoomNumberExist(Integer roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber)
                .isPresent();
    }
}
