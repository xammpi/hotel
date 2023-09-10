package org.calisto.hotel.sevices;

import org.calisto.hotel.exception.ResourceNotFoundException;
import org.calisto.hotel.exception.RoomAlreadyExistsException;
import org.calisto.hotel.entity.Room;
import org.calisto.hotel.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public Page<Room> getAllRooms(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    public Room findById(Integer id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", id));
    }

    public Room findByRoomNumber(Integer roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Room", "room number", roomNumber));
    }

    @Modifying
    @Transactional
    public Room save(Room room) {
        if (isRoomExist(room.getRoomNumber())) {
            throw new RoomAlreadyExistsException(
                    String.format("The room with number: %s is already exist",
                            room.getRoomNumber())
            );
        }
        return roomRepository.save(room);
    }

    @Modifying
    @Transactional
    public Room update(Integer id, Room updatedRoom) {
        Room room = findById(id);
        room.setReservations(updatedRoom.getReservations());
        room.setRoomNumber(updatedRoom.getRoomNumber());
        room.setCapacity(updatedRoom.getCapacity());
        updatedRoom.setId(id);
        return roomRepository.save(updatedRoom);
    }

    @Modifying
    @Transactional
    public void deleteById(Integer id) {
        roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room", "id", id));
        roomRepository.deleteById(id);
    }

    private boolean isRoomExist(Integer roomNumber) {
        Optional<Room> existingRoom = roomRepository.findByRoomNumber(roomNumber);
        return existingRoom.isPresent();
    }
}
