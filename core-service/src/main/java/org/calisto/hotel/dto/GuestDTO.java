package org.calisto.hotel.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "GuestDTO Model Information")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestDTO {
    private Integer id;
    @Schema(description = "Guest First Name")
    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[a-zA-Z]{1,20}$", message = "Invalid format or length")
    private String firstName;
    @Schema(description = "Guest Last Name")
    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[a-zA-Z]{1,20}$", message = "Invalid format or length")
    private String lastName;
    @Schema(description = "Guest Email")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;
    @Schema(description = "Guest Phone Number")
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(\\+)?(?:[0-9] ?){6,14}[0-9]$",
            message = "Invalid phone number format")
    private String phoneNumber;
}
