package ec.com.bank.domain.model.exception;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException{
    private final HttpStatus status;
    private final String     code;

    protected ApplicationException(String message,
                                   String code,
                                   HttpStatus status) {
        super(message);
        this.code   = code;
        this.status = status;
    }

}
