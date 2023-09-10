package org.calisto.hotel.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.calisto.hotel.dto.RoomDTO;
import org.calisto.hotel.entity.Room;
import org.calisto.hotel.sevices.RoomService;
import org.calisto.hotel.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Rooms",
        description = "Create Room, Update Room, Get Room, Get All Rooms, Delete Room"
)
@RestController
@RequestMapping("/api/rooms")
public class RoomRestController {
    private final RoomService roomService;
    private final ModelConverter modelMapper;

    @Autowired
    public RoomRestController(RoomService roomService, ModelConverter modelMapper) {
        this.roomService = roomService;
        this.modelMapper = modelMapper;
    }

    @Operation(
            summary = "Get All Rooms REST API",
            description = "Get All Rooms REST API is used to get all rooms from the database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        List<RoomDTO> rooms = modelMapper.convertRoomToDTO(roomService.findAll());
        return ResponseEntity.ok(rooms);
    }

    @Operation(
            summary = "Get Rooms By Page REST API",
            description = "Get Rooms By Page REST API is used to get all rooms using pagination from the database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping("/search")
    public ResponseEntity<Page<RoomDTO>> getRoomsByPage(Pageable pageable) {
        Page<Room> roomPage = roomService.getAllRooms(pageable);
        List<RoomDTO> roomDTO = modelMapper.convertRoomToDTO(roomPage.getContent());
        Page<RoomDTO> pages = new PageImpl<>(roomDTO, roomPage.getPageable(), roomPage.getTotalPages());
        return ResponseEntity.ok(pages);
    }

    @Operation(
            summary = "Create Room REST API",
            description = "Create Room REST API is used to save room in database"
    )
    @ApiResponse(responseCode = "201", description = "HTTP Status CREATED")
    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody @Valid RoomDTO roomDTO) {
        Room createdRoom = roomService.save(modelMapper.convertToRoom(roomDTO));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.convertRoomToDTO(createdRoom));
    }

    @Operation(
            summary = "Get Room REST API",
            description = "Get Room REST API is used to get room by id from database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoom(@PathVariable("id") int id) {
        Room room = roomService.findById(id);
        return ResponseEntity.ok(modelMapper.convertRoomToDTO(room));
    }

    @Operation(
            summary = "Edit Room REST API",
            description = "Edit Room REST API Used to update an existing room by id in the database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> editRoom(@RequestBody @Valid RoomDTO roomDTO, @PathVariable("id") int id) {
        Room room = roomService.update(id, modelMapper.convertToRoom(roomDTO));
        return ResponseEntity.ok(modelMapper.convertRoomToDTO(room));
    }

    @Operation(
            summary = "Delete Room REST API",
            description = "Delete Room REST API is used to delete room by id from database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteRoom(@PathVariable("id") int id) {
        roomService.deleteById(id);
        return ResponseEntity.ok("Room successfully deleted!");
    }
}
