package org.calisto.hotel.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.calisto.hotel.dto.GuestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(
        name = "Guests",
        description = "Create Guest, Update Guest, Get Guest, Get All Guests, Delete Guest"
)
public interface GuestController {
    @Operation(
            summary = "Get All Guests REST API",
            description = "Get All Guests REST API is used to get all rooms from the database"
    )

    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    ResponseEntity<List<GuestDTO>> getAllGuests();

    @Operation(
            summary = "Get Guests By Page REST API",
            description = "Get Guests By Page REST API is used to get all guests using pagination from the database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    ResponseEntity<Page<GuestDTO>> getGuestsByPage(Pageable pageable);

    @Operation(
            summary = "Create Guest REST API",
            description = "Create Guest REST API is used to save guest in database"
    )
    @ApiResponse(responseCode = "201", description = "HTTP Status CREATED")
    ResponseEntity<GuestDTO> createGuest(@RequestBody @Valid GuestDTO guestDTO);

    @Operation(
            summary = "Get Guest REST API",
            description = "Get Guest REST API is used to get guest by id from database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    ResponseEntity<GuestDTO> getGuest(@PathVariable("id") int id);

    @Operation(
            summary = "Edit Guest REST API",
            description = "Edit Guest REST API Used to update an existing guest by id in the database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    ResponseEntity<GuestDTO> editGuest(@RequestBody @Valid GuestDTO guestDTO, @PathVariable("id") int id);

    @Operation(
            summary = "Delete Guest REST API",
            description = "Delete Guest REST API is used to delete guest by id from database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    ResponseEntity<String> deleteGuest(@PathVariable("id") int id);
}
