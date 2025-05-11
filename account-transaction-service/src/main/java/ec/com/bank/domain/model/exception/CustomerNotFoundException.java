package ec.com.bank.domain.model.exception;

import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends ApplicationException {

    public CustomerNotFoundException(String id) {
        super("No existe el cliente con id " + id, "CUSTOMER_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
