package ec.com.bank.domain.model.exception;

import org.springframework.http.HttpStatus;

public class InsufficientBalanceException extends ApplicationException {

    public InsufficientBalanceException(String value) {
        super("Saldo insuficiente en la cuenta " + value, "ACCOUNT_INSUFFICIENT", HttpStatus.BAD_REQUEST);
    }}