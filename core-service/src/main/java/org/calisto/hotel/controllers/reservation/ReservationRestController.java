package org.calisto.hotel.controllers.reservation;

import jakarta.validation.Valid;
import org.calisto.hotel.dto.ReservationDTO;
import org.calisto.hotel.exception.ErrorDetails;
import org.calisto.hotel.exception.ErrorHandler;
import org.calisto.hotel.exception.ReservationAlreadyExistsException;
import org.calisto.hotel.sevices.ReservationService;
import org.calisto.hotel.sevices.impl.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationRestController implements ReservationController,
        ErrorHandler<ReservationAlreadyExistsException> {
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
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody @Valid ReservationDTO reservationDTO)
            throws ReservationAlreadyExistsException {
        ReservationDTO createdReservation = reservationService.save(reservationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ReservationDTO> showReservation(@PathVariable("id") int id) {
        ReservationDTO reservation = reservationService.findById(id);
        return ResponseEntity.ok(reservation);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<ReservationDTO> editReservation(@RequestBody @Valid ReservationDTO reservationDTO,
                                                          @PathVariable("id") int id) {
        ReservationDTO reservation = reservationService.update(id, reservationDTO);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<String> deleteReservation(@PathVariable("id") int id) {
        reservationService.deleteById(id);
        return ResponseEntity.ok("Reservation successfully deleted!");
    }

    @ExceptionHandler(ReservationAlreadyExistsException.class)
    @Override
    public ResponseEntity<ErrorDetails> handleAlreadyExistsException(
            ReservationAlreadyExistsException exception,
            WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
                exception.getMessage(),
                webRequest.getDescription(false),
                String.valueOf(HttpStatus.CONFLICT.value())
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }
}
