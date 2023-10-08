package org.calisto.hotel.controllers.room;

import jakarta.validation.Valid;
import org.calisto.hotel.dto.RoomDTO;
import org.calisto.hotel.exception.ErrorDetails;
import org.calisto.hotel.exception.ErrorHandler;
import org.calisto.hotel.exception.RoomAlreadyExistsException;
import org.calisto.hotel.sevices.RoomService;
import org.calisto.hotel.sevices.impl.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomRestController implements RoomController, ErrorHandler<RoomAlreadyExistsException> {
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
    public ResponseEntity<RoomDTO> createRoom(@RequestBody @Valid RoomDTO roomDTO)
            throws RoomAlreadyExistsException {
        RoomDTO createdRoom = roomService.save(roomDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<RoomDTO> getRoom(@PathVariable("id") int id) throws IllegalAccessException, InstantiationException {
        RoomDTO room = roomService.findById(id);
        return ResponseEntity.ok(room);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<RoomDTO> editRoom(@RequestBody @Valid RoomDTO roomDTO,
                                            @PathVariable("id") int id) {
        RoomDTO room = roomService.update(id, roomDTO);
        return ResponseEntity.ok(room);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<String> deleteRoom(@PathVariable("id") int id) {
        roomService.deleteById(id);
        return ResponseEntity.ok("Room successfully deleted!");
    }

    @ExceptionHandler(RoomAlreadyExistsException.class)
    @Override
    public ResponseEntity<ErrorDetails> handleAlreadyExistsException(
            RoomAlreadyExistsException exception,
            WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
                exception.getMessage(),
                webRequest.getDescription(false),
                String.valueOf(HttpStatus.CONFLICT.value())
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }
}
