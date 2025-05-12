package ec.com.bank.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class TransactionWithAccountDto {

    private Long id;
    private Instant dateTransaction;
    private String typeTransaction;
    private Double value;
    private BigDecimal balance;
    private String state;
    private Long accountId;
    private String numberAccount;
    private String typeAccount;
    private BigDecimal initialBalance;
    private String accountState;
    private Instant dateCreate;
}
