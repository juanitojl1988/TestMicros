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
public class TransactionCreateDto {

    @NotBlank(message = "El número de cuenta no puede estar vacío")
    @Size(min = 10, max = 20, message = "El número de cuenta debe tener entre {min} y {max} caracteres")
    private String numberAccount;


    @NotNull(message = "El monto es obligatorio")
    private BigDecimal amount;

    @AssertTrue(message = "El monto no puede ser cero")
    private boolean isAmountNotZero() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) != 0;
    }


}