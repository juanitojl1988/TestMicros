package ec.com.bank.domain.model.dto;

import ec.com.bank.domain.model.enums.AccountConstant;
import ec.com.bank.domain.model.enums.TransactionConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class TransactionDto {


    private Long id;
    private Instant dateTransaction;
    private TransactionConstant.TypeTransaction typeTransaction;
    private BigDecimal value;
    private BigDecimal balance;
    private AccountConstant.State state;
    private Long accountId;
    private Instant dateFrom;
    private Instant dateTo;
    private TransactionDto lastTransaction;

    public TransactionDto() {

    }
}