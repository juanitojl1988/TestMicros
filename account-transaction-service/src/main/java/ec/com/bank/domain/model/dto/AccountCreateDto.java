package ec.com.bank.domain.model.dto;

import ec.com.bank.domain.model.enums.AccountConstant;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class AccountCreateDto {

    @NotBlank(message = "El número de cuenta no puede estar vacío")
    @Size(min = 10, message = "El número de cuenta debe tener minino {min} caracteres")
    private String numberAccount;

    @NotNull(message = "El tipo de cuenta es obligatorio")
    private AccountConstant.TypeAccount typeAccount;

    @NotNull(message = "El saldo inicial es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El saldo inicial no puede ser negativo")
    private BigDecimal initialBalance;

    @NotNull(message = "El estado de la cuenta es obligatorio")
    private AccountConstant.State state;

    @NotNull(message = "El ID de cliente es obligatorio")
    @Positive(message = "El ID de cliente debe ser un número positivo")
    private Long customerId;
}
