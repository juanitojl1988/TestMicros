package ec.com.bank.domain.exception;

import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends ApplicationException {

    public ServiceUnavailableException(String detail) {
        super(detail, "SERVICE_UNAVAILABLE", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
