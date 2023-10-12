package org.calisto.hotel.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "ReservationDTO Model Information")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO extends BaseDTO {
    @Schema(description = "Reservation Check-in Date")
    @NotNull(message = "Check-in date is required")
    @FutureOrPresent(message = "Check-in date should take place in the future or today")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkInDate;
    @Schema(description = "Reservation Check-out Date")
    @Future(message = "Check-out must be in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Check-out date is required")
    private LocalDate checkOutDate;
    @Schema(description = "Reservation RoomDTO")
    @NotNull(message = "Room is required")
    @JsonProperty("room")
    @Valid
    private RoomDTO roomDto;
    @Schema(description = "Reservation GuestDTO")
    @NotNull(message = "Guest is required")
    @JsonProperty("guest")
    @Valid
    private GuestDTO guestDto;
}
