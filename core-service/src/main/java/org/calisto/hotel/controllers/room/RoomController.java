package org.calisto.hotel.controllers.room;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.calisto.hotel.dto.RoomDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(
        name = "Rooms",
        description = "Create Room, Update Room, Get Room, Get All Rooms, Delete Room"
)
public interface RoomController {

    @Operation(
            summary = "Get All Rooms REST API",
            description = "Get All Rooms REST API is used to get all rooms from the database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    ResponseEntity<List<RoomDTO>> getAllRooms();

    @Operation(
            summary = "Get Rooms By Page REST API",
            description = "Get Rooms By Page REST API is used to get all rooms using pagination from the database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    ResponseEntity<Page<RoomDTO>> getRoomsByPage(Pageable pageable);

    @Operation(
            summary = "Create Room REST API",
            description = "Create Room REST API is used to save room in database"
    )
    @ApiResponse(responseCode = "201", description = "HTTP Status CREATED")
    ResponseEntity<RoomDTO> createRoom(RoomDTO roomDTO);

    @Operation(
            summary = "Get Room REST API",
            description = "Get Room REST API is used to get room by id from database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    ResponseEntity<RoomDTO> getRoom(int id);

    @Operation(
            summary = "Edit Room REST API",
            description = "Edit Room REST API Used to update an existing room by id in the database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    ResponseEntity<RoomDTO> editRoom(RoomDTO roomDTO, int id);

    @Operation(
            summary = "Delete Room REST API",
            description = "Delete Room REST API is used to delete room by id from database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    ResponseEntity<String> deleteRoom(int id);
}
