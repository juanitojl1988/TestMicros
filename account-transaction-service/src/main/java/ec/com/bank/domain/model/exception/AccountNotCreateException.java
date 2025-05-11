package ec.com.bank.domain.model.exception;

import org.springframework.http.HttpStatus;

public class AccountNotCreateException extends ApplicationException {

    public AccountNotCreateException(String id) {
        super("Cuenta no creada  con id " + id, "ACCOUNT_NOT_CREATE", HttpStatus.BAD_REQUEST);
    }
}
