package org.calisto.hotel.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.calisto.hotel.dto.ReservationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(
        name = "Reservations",
        description = "Create Reservation, Update Reservation, Get Reservation, Get All Reservations, Delete Reservation"
)
public interface ReservationController {
    @Operation(
            summary = "Get All Reservations REST API",
            description = "Get All Reservations REST API is used to get all reservations from the database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    ResponseEntity<List<ReservationDTO>> getAllReservations();

    @Operation(
            summary = "Get Reservations By Page REST API",
            description = "Get Reservations By Page REST API is used to get all reservations" +
                    " using pagination from the database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    ResponseEntity<Page<ReservationDTO>> getReservationsByPage(Pageable pageable);

    @Operation(
            summary = "Create Reservation REST API",
            description = "Create Reservation REST API is used to save reservation in database"
    )
    @ApiResponse(responseCode = "201", description = "HTTP Status CREATED")
    ResponseEntity<ReservationDTO> createReservation(@RequestBody @Valid ReservationDTO reservationDTO);

    @Operation(
            summary = "Get Reservation REST API",
            description = "Get Reservation REST API is used to get reservation by id from database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    ResponseEntity<ReservationDTO> getReservation(@PathVariable("id") int id);

    @Operation(
            summary = "Edit Reservation REST API",
            description = "Edit Reservation REST API Used to update an existing reservation by id in the database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    ResponseEntity<ReservationDTO> editReservation(@RequestBody @Valid ReservationDTO reservationDTO,
                                                   @PathVariable("id") int id);

    @Operation(
            summary = "Delete Reservation REST API",
            description = "Delete Reservation REST API is used to delete reservation by id from database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    ResponseEntity<String> deleteReservation(@PathVariable("id") int id);
}
