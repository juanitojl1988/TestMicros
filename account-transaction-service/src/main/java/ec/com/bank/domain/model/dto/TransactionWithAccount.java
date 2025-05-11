package ec.com.bank.domain.model.dto;

import java.math.BigDecimal;
import java.time.Instant;

public interface TransactionWithAccount {

    Long    getId();
    Instant getDateTransaction();
    String  getTypeTransaction();
    BigDecimal getValue();
    BigDecimal getBalance();
    String  getState();

    // Campos de la cuenta
    Long    getAccountId();
    String  getNumberAccount();
    String  getTypeAccount();
    BigDecimal getInitialBalance();
    String  getAccountState();
    Instant getDateCreate();
}
