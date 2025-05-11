package ec.com.bank.domain.model.exception;

import org.springframework.http.HttpStatus;

public class TransactionCreationException extends ApplicationException {
    public TransactionCreationException(String value) {
        super("Error al Crear trasnaccion " + value, "TRANSSACTION_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}
