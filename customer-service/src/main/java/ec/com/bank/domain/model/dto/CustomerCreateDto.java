package ec.com.bank.domain.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;



@Data
@AllArgsConstructor
@Builder
public class CustomerCreateDto {

    @NotBlank(message = "Identification is required")
    @Size(max = 50, message = "Identification must be at most 50 characters")
    private String identification;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @NotNull(message = "Gender is required")
    @Pattern(regexp = "[MF]", message = "Gender must be 'M' or 'F'")
    @Size(min = 1, max = 1, message = "State must be exactly one character")
    private String gender;

    @Min(value = 0, message = "Age must be non-negative")
    @Max(value = 150, message = "Age must be realistic")
    private int age;

    @NotBlank(message = "Addresses is required")
    @Size(max = 255, message = "Addresses must be at most 255 characters")
    private String addresses;

    @NotBlank(message = "Phone is required")
    @Pattern(
            regexp = "\\+?\\d{7,15}",
            message = "Phone must be 7–15 digits, optionally starting with '+'"
    )
    private String phone;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;

    @NotBlank(message = "State is required")
    @Pattern(regexp = "[AE]", message = "State must be 'A' or 'E'")
    @Size(min = 1, max = 1, message = "State must be exactly one character")
    private String state;


}
