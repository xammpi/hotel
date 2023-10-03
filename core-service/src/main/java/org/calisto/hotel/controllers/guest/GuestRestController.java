package org.calisto.hotel.controllers.guest;

import jakarta.validation.Valid;
import org.calisto.hotel.dto.GuestDTO;
import org.calisto.hotel.exception.ErrorDetails;
import org.calisto.hotel.exception.ErrorHandler;
import org.calisto.hotel.exception.GuestAlreadyExistException;
import org.calisto.hotel.sevices.guest.GuestService;
import org.calisto.hotel.sevices.guest.GuestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;


@RestController
@RequestMapping("/api/guests")
public class GuestRestController implements GuestController, ErrorHandler<GuestAlreadyExistException> {
    private final GuestService guestService;

    @Autowired
    public GuestRestController(GuestServiceImpl guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    @Override
    public ResponseEntity<List<GuestDTO>> getAllGuests() {
        List<GuestDTO> guestDTO = guestService.findAll();
        return ResponseEntity.ok(guestDTO);
    }

    @GetMapping("/search")
    @Override
    public ResponseEntity<Page<GuestDTO>> getGuestsByPage(Pageable pageable) {
        Page<GuestDTO> guestPage = guestService.findAll(pageable);
        return ResponseEntity.ok(guestPage);
    }

    @PostMapping
    @Override
    public ResponseEntity<GuestDTO> createGuest(@RequestBody @Valid GuestDTO guestDTO)
            throws GuestAlreadyExistException {
        GuestDTO guest = guestService.save(guestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(guest);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<GuestDTO> getGuest(@PathVariable("id") int id) {
        GuestDTO guestDTO = guestService.findById(id);
        return ResponseEntity.ok(guestDTO);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<GuestDTO> editGuest(@RequestBody @Valid GuestDTO guestDTO,
                                              @PathVariable("id") int id) {
        GuestDTO guest = guestService.update(id, guestDTO);
        return ResponseEntity.ok(guest);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<String> deleteGuest(@PathVariable("id") int id) {
        guestService.deleteById(id);
        return ResponseEntity.ok("Guest successfully deleted!");
    }

    @ExceptionHandler(GuestAlreadyExistException.class)
    @Override
    public ResponseEntity<ErrorDetails> handleAlreadyExistsException(
            GuestAlreadyExistException exception,
            WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
                exception.getMessage(),
                webRequest.getDescription(false),
                String.valueOf(HttpStatus.CONFLICT.value())
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }
}
