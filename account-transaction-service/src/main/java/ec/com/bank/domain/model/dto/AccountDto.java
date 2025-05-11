package ec.com.bank.domain.model.dto;

import ec.com.bank.domain.model.enums.AccountConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;


@Data
@AllArgsConstructor
@Builder
public class AccountDto {

    private Long id;
    private String numberAccount;
    private AccountConstant.TypeAccount typeAccount;
    private BigDecimal initialBalance;
    private AccountConstant.State state;
    private Long customerId;
    private Instant dateCreate;
    private TransactionDto transactionInitDto;

    public AccountDto() {

    }
}