package org.calisto.hotel.controllers.impl;

import jakarta.validation.Valid;
import org.calisto.hotel.controllers.RoomController;
import org.calisto.hotel.dto.RoomDTO;
import org.calisto.hotel.exception.ResourceConflictException;
import org.calisto.hotel.exception.ResourceNotFoundException;
import org.calisto.hotel.sevices.RoomService;
import org.calisto.hotel.sevices.impl.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.calisto.hotel.util.constants.ExceptionConstants.CONFLICT_EXCEPTION_TEMPLATE;
import static org.calisto.hotel.util.constants.ExceptionConstants.NOT_FOUND_EXCEPTION_TEMPLATE;
import static org.calisto.hotel.util.constants.Messages.SUCCESSFULLY_DELETED;
import static org.calisto.hotel.util.constants.NameConstants.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/rooms")
public class RoomRestController implements RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomRestController(RoomServiceImpl roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    @Override
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        List<RoomDTO> rooms = roomService.findAll();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/search")
    @Override
    public ResponseEntity<Page<RoomDTO>> getRoomsByPage(Pageable pageable) {
        Page<RoomDTO> roomPage = roomService.findAll(pageable);
        return ResponseEntity.ok(roomPage);
    }

    @PostMapping
    @Override
    public ResponseEntity<RoomDTO> createRoom(@RequestBody @Valid RoomDTO roomDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(roomService.save(roomDTO));
        } catch (ResourceConflictException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    String.format(CONFLICT_EXCEPTION_TEMPLATE,
                            ROOM_TXT,
                            ROOM_NUMBER_TXT,
                            roomDTO.getRoomNumber()));
        }
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<RoomDTO> getRoom(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(roomService.findById(id));
        } catch (ResourceNotFoundException exception) {
            throw new ResponseStatusException(
                    NOT_FOUND,
                    String.format(NOT_FOUND_EXCEPTION_TEMPLATE,
                            ROOM_TXT,
                            ID_TXT,
                            id)
            );
        }
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<RoomDTO> editRoom(@RequestBody @Valid RoomDTO roomDTO,
                                            @PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(roomService.update(id, roomDTO));
        } catch (ResourceNotFoundException exception) {
            throw new ResponseStatusException(
                    NOT_FOUND,
                    String.format(NOT_FOUND_EXCEPTION_TEMPLATE,
                            ROOM_TXT,
                            ID_TXT,
                            id)
            );
        }
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<String> deleteRoom(@PathVariable("id") int id) {
        try {
            roomService.deleteById(id);
            return ResponseEntity.ok(String.format(SUCCESSFULLY_DELETED, ROOM_TXT));
        } catch (ResourceNotFoundException exception) {
            throw new ResponseStatusException(
                    NOT_FOUND,
                    String.format(NOT_FOUND_EXCEPTION_TEMPLATE,
                            ROOM_TXT,
                            ID_TXT,
                            id)
            );
        }
    }
}
