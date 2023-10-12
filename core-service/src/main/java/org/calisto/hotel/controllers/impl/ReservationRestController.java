package org.calisto.hotel.controllers.impl;

import jakarta.validation.Valid;
import org.calisto.hotel.controllers.ReservationController;
import org.calisto.hotel.dto.ReservationDTO;
import org.calisto.hotel.exception.ResourceConflictException;
import org.calisto.hotel.exception.ResourceNotFoundException;
import org.calisto.hotel.sevices.ReservationService;
import org.calisto.hotel.sevices.impl.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.calisto.hotel.utils.constants.ExceptionConstants.CONFLICT_RESERVATION_TEMPLATE;
import static org.calisto.hotel.utils.constants.ExceptionConstants.NOT_FOUND_EXCEPTION_TEMPLATE;
import static org.calisto.hotel.utils.constants.Messages.SUCCESSFULLY_DELETED;
import static org.calisto.hotel.utils.constants.NameConstants.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/reservations")
public class ReservationRestController implements ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationRestController(ReservationServiceImpl reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    @Override
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<ReservationDTO> reservationDTO = reservationService.findAll();
        return ResponseEntity.ok(reservationDTO);
    }

    @GetMapping("/search")
    @Override
    public ResponseEntity<Page<ReservationDTO>> getReservationsByPage(Pageable pageable) {
        Page<ReservationDTO> reservationPage = reservationService.findAll(pageable);
        return ResponseEntity.ok(reservationPage);
    }

    @PostMapping
    @Override
    public ResponseEntity<ReservationDTO> createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.save(reservationDTO));
        } catch (ResourceConflictException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    String.format(CONFLICT_RESERVATION_TEMPLATE,
                            reservationDTO.getRoomDto().getId(),
                            reservationDTO.getCheckInDate(),
                            reservationDTO.getCheckOutDate())
            );
        }
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(reservationService.findById(id));
        } catch (ResourceNotFoundException exception) {
            throw new ResponseStatusException(
                    NOT_FOUND,
                    String.format(NOT_FOUND_EXCEPTION_TEMPLATE,
                            RESERVATION_TXT,
                            ID_TXT,
                            id)
            );
        }
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<ReservationDTO> editReservation(@RequestBody @Valid ReservationDTO reservationDTO,
                                                          @PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(reservationService.update(id, reservationDTO));
        } catch (ResourceNotFoundException exception) {
            throw new ResponseStatusException(
                    NOT_FOUND,
                    String.format(NOT_FOUND_EXCEPTION_TEMPLATE,
                            RESERVATION_TXT,
                            ID_TXT,
                            id)
            );
        } catch (ResourceConflictException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    String.format(CONFLICT_RESERVATION_TEMPLATE,
                            reservationDTO.getRoomDto().getId(),
                            reservationDTO.getCheckInDate(),
                            reservationDTO.getCheckOutDate())
            );
        }
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<String> deleteReservation(@PathVariable("id") int id) {
        try {
            reservationService.deleteById(id);
            return ResponseEntity.ok(String.format(SUCCESSFULLY_DELETED, RESERVATION_TXT));
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
