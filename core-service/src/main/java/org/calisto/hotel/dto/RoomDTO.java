package org.calisto.hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "RoomDTO Model Information")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO extends BaseDTO {
    @Schema(description = "Room Number")
    @NotNull(message = "Room number is required")
    @Min(value = 1, message = "Room number must be at least 1")
    private Integer roomNumber;
    @Schema(description = "Room Capacity")
    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 6, message = "Capacity must be at most 6")
    private int capacity;
}
