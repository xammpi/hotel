package org.calisto.hotel.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.calisto.hotel.dto.ReservationDTO;
import org.calisto.hotel.entity.Reservation;
import org.calisto.hotel.sevices.ReservationService;
import org.calisto.hotel.util.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Reservations",
        description = "Create Reservation, Update Reservation, Get Reservation, Get All Reservations, Delete Reservation"
)
@RestController
@RequestMapping("/api/reservations")
public class ReservationRestController {
    private final ReservationService reservationService;
    private final ModelConverter modelMapper;

    @Autowired
    public ReservationRestController(ReservationService reservationService, ModelConverter modelMapper) {
        this.reservationService = reservationService;
        this.modelMapper = modelMapper;
    }

    @Operation(
            summary = "Get All Reservations REST API",
            description = "Get All Reservations REST API is used to get all reservations from the database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<ReservationDTO> reservationDTO = modelMapper.convertReservationToDTO(reservationService.findAll());
        return ResponseEntity.ok(reservationDTO);
    }

    @Operation(
            summary = "Get Reservations By Page REST API",
            description = "Get Reservations By Page REST API is used to get all reservations" +
                    " using pagination from the database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping("/search")
    public ResponseEntity<Page<ReservationDTO>> getReservationsByPage(Pageable pageable) {
        Page<Reservation> reservationPage = reservationService.getAllReservations(pageable);
        List<ReservationDTO> reservationDTO = modelMapper.convertReservationToDTO(reservationPage.getContent());
        Page<ReservationDTO> pages = new PageImpl<>(reservationDTO, reservationPage.getPageable(),
                reservationPage.getTotalPages());
        return ResponseEntity.ok(pages);
    }

    @Operation(
            summary = "Create Reservation REST API",
            description = "Create Reservation REST API is used to save reservation in database"
    )
    @ApiResponse(responseCode = "201", description = "HTTP Status CREATED")
    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody @Valid ReservationDTO reservationDTO) {
        Reservation createdReservation = reservationService.save(modelMapper.convertToReservation(reservationDTO));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.convertReservationToDTO(createdReservation));
    }

    @Operation(
            summary = "Get Reservation REST API",
            description = "Get Reservation REST API is used to get reservation by id from database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> showReservation(@PathVariable("id") int id) {
        Reservation reservation = reservationService.findById(id);
        return new ResponseEntity<>(modelMapper.convertReservationToDTO(reservation), HttpStatus.OK);
    }

    @Operation(
            summary = "Edit Reservation REST API",
            description = "Edit Reservation REST API Used to update an existing reservation by id in the database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> editReservation(@RequestBody @Valid ReservationDTO reservationDTO,
                                                          BindingResult result, @PathVariable("id") int id) {
        Reservation reservation = reservationService.update(id, modelMapper.convertToReservation(reservationDTO));
        return ResponseEntity.ok(modelMapper.convertReservationToDTO(reservation));
    }

    @Operation(
            summary = "Delete Reservation REST API",
            description = "Delete Reservation REST API is used to delete reservation by id from database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@PathVariable("id") int id) {
        reservationService.deleteById(id);
        return ResponseEntity.ok("Reservation successfully deleted!");
    }
}
