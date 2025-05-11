package ec.com.bank.domain.exception;

import org.springframework.http.HttpStatus;

public class CustomerAlreadyExistsException extends ApplicationException {

    public CustomerAlreadyExistsException(String value) {
        super("No existe el cliente con id " + value, "CUSTOMER_ALREADY_EXISTS", HttpStatus.BAD_REQUEST);
    }
}
