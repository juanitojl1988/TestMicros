package ec.com.bank.domain.model.exception;

import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends ApplicationException {
    public AccountNotFoundException(String id) {
        super("No existe el cuenta con id " + id, "ACCOUNT_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
