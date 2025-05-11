package ec.com.bank.domain.model.exception;

import org.springframework.http.HttpStatus;

public class NoTransactionsFoundException extends ApplicationException {
    public NoTransactionsFoundException(String value) {
        super("No existe el transacion con cuenta " + value, "TRANSSACTION_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}