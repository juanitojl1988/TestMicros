package ec.com.bank.domain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "{"
                    + "\"timestamp\":\"" + timestamp + "\","
                    + "\"status\":" + status + ","
                    + "\"error\":\"" + error + "\","
                    + "\"code\":\"" + code + "\","
                    + "\"message\":\"" + message + "\","
                    + "\"path\":\"" + path + "\""
                    + "}";
        }
    }
}