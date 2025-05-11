package ec.com.bank.domain.model.exception;

import org.springframework.http.HttpStatus;

public class AccountAlreadyExistsException extends ApplicationException {

    public AccountAlreadyExistsException(String value) {
        super("ya existe la Cuenta con id " + value, "ACCOUNT_ALREADY_EXISTS", HttpStatus.BAD_REQUEST);
    }
}