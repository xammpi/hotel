package org.calisto.hotel.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.calisto.hotel.dto.GuestDTO;
import org.calisto.hotel.entity.Guest;
import org.calisto.hotel.sevices.GuestService;
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
        name = "Guests",
        description = "Create Guest, Update Guest, Get Guest, Get All Guests, Delete Guest"
)
@RestController
@RequestMapping("/api/guests")
public class GuestRestController {
    private final GuestService guestService;
    private final ModelConverter modelMapper;

    @Autowired
    public GuestRestController(GuestService guestService, ModelConverter modelMapper) {
        this.guestService = guestService;
        this.modelMapper = modelMapper;
    }

    @Operation(
            summary = "Get All Guests REST API",
            description = "Get All Guests REST API is used to get all rooms from the database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping
    public ResponseEntity<List<GuestDTO>> getAllGuests() {
        List<GuestDTO> guestDTO = modelMapper.convertGuestToDTO(guestService.findAll());
        return ResponseEntity.ok(guestDTO);
    }

    @Operation(
            summary = "Get Guests By Page REST API",
            description = "Get Guests By Page REST API is used to get all guests using pagination from the database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping("/search")
    public ResponseEntity<Page<GuestDTO>> getGuestsByPage(Pageable pageable) {
        Page<Guest> guestPage = guestService.getAllGuests(pageable);
        List<GuestDTO> guestDTO = modelMapper.convertGuestToDTO(guestPage.getContent());
        Page<GuestDTO> pages = new PageImpl<>(guestDTO, guestPage.getPageable(), guestPage.getTotalPages());
        return ResponseEntity.ok(pages);
    }

    @Operation(
            summary = "Create Guest REST API",
            description = "Create Guest REST API is used to save guest in database"
    )
    @ApiResponse(responseCode = "201", description = "HTTP Status CREATED")
    @PostMapping
    public ResponseEntity<GuestDTO> createGuest(@RequestBody @Valid GuestDTO guestDTO) {
        Guest guest = guestService.save(modelMapper.convertToGuest(guestDTO));
        return new ResponseEntity<>(modelMapper.convertGuestToDTO(guest), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Guest REST API",
            description = "Get Guest REST API is used to get guest by id from database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping("/{id}")
    public ResponseEntity<GuestDTO> getGuest(@PathVariable("id") int id) {
        GuestDTO guestDTO = modelMapper.convertGuestToDTO(guestService.findById(id));
        return ResponseEntity.ok(guestDTO);
    }

    @Operation(
            summary = "Edit Guest REST API",
            description = "Edit Guest REST API Used to update an existing guest by id in the database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @PutMapping("/{id}")
    public ResponseEntity<GuestDTO> editGuest(@RequestBody @Valid GuestDTO guestDTO, @PathVariable("id") int id) {
        Guest guest = guestService.update(id, modelMapper.convertToGuest(guestDTO));
        return ResponseEntity.ok(modelMapper.convertGuestToDTO(guest));
    }

    @Operation(
            summary = "Delete Guest REST API",
            description = "Delete Guest REST API is used to delete guest by id from database"
    )
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteGuest(@PathVariable("id") int id) {
        guestService.deleteById(id);
        return ResponseEntity.ok("Guest successfully deleted!");
    }
}
