package ec.com.bank.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class ErrorResponse {
    private OffsetDateTime timestamp;
    private int           status;
    private String        error;
    private String        code;
    private String        message;
    private String        path;
}