package ec.com.bank.domain.model.exception;

import org.springframework.http.HttpStatus;

public class AccountInactiveException extends ApplicationException {
    public AccountInactiveException(String value) {
        super("Cuenta inactiva con numero " + value, "INACTIVE_ACCOUNT", HttpStatus.BAD_REQUEST);
    }
}