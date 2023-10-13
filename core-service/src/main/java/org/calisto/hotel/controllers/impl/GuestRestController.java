package org.calisto.hotel.controllers.impl;

import jakarta.validation.Valid;
import org.calisto.hotel.controllers.GuestController;
import org.calisto.hotel.dto.GuestDTO;
import org.calisto.hotel.exception.ResourceConflictException;
import org.calisto.hotel.exception.ResourceNotFoundException;
import org.calisto.hotel.sevices.GuestService;
import org.calisto.hotel.sevices.impl.GuestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.calisto.hotel.utils.constants.ExceptionConstants.CONFLICT_EXCEPTION_TEMPLATE;
import static org.calisto.hotel.utils.constants.ExceptionConstants.NOT_FOUND_EXCEPTION_TEMPLATE;
import static org.calisto.hotel.utils.constants.Messages.SUCCESSFULLY_DELETED;
import static org.calisto.hotel.utils.constants.NameConstants.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestController
@RequestMapping("/api/guests")
public class GuestRestController implements GuestController {
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
    public ResponseEntity<GuestDTO> createGuest(@RequestBody @Valid GuestDTO guestDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(guestService.save(guestDTO));
        } catch (ResourceConflictException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    String.format(CONFLICT_EXCEPTION_TEMPLATE,
                            GUEST_TXT,
                            EMAIL_TXT,
                            guestDTO.getEmail())
            );
        }
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<GuestDTO> getGuest(@PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(guestService.findById(id));
        } catch (ResourceNotFoundException exception) {
            throw new ResponseStatusException(
                    NOT_FOUND,
                    String.format(NOT_FOUND_EXCEPTION_TEMPLATE,
                            GUEST_TXT,
                            ID_TXT,
                            id)
            );
        }
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<GuestDTO> editGuest(@RequestBody @Valid GuestDTO guestDTO,
                                              @PathVariable("id") int id) {
        try {
            return ResponseEntity.ok(guestService.update(id, guestDTO));
        } catch (ResourceNotFoundException exception) {
            throw new ResponseStatusException(
                    NOT_FOUND,
                    String.format(NOT_FOUND_EXCEPTION_TEMPLATE,
                            GUEST_TXT,
                            ID_TXT,
                            id)
            );
        }
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<String> deleteGuest(@PathVariable("id") int id) {
        try {
            guestService.deleteById(id);
            return ResponseEntity.ok(String.format(SUCCESSFULLY_DELETED, GUEST_TXT));
        } catch (ResourceNotFoundException exception) {
            throw new ResponseStatusException(
                    NOT_FOUND,
                    String.format(NOT_FOUND_EXCEPTION_TEMPLATE,
                            GUEST_TXT,
                            ID_TXT,
                            id)
            );
        }
    }
}
