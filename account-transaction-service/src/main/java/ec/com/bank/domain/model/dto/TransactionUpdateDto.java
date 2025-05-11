package ec.com.bank.domain.model.dto;

import ec.com.bank.domain.model.enums.AccountConstant;
import ec.com.bank.domain.model.enums.TransactionConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class TransactionUpdateDto {
    private TransactionConstant.TypeTransaction typeTransaction;
    private BigDecimal value;
    private BigDecimal balance;
    private AccountConstant.State state;

}
