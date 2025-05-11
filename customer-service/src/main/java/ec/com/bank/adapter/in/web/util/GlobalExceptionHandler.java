package ec.com.bank.adapter.in.web.util;

import ec.com.bank.domain.exception.ApplicationException;
import ec.com.bank.domain.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public Mono<ErrorResponse> handleAny(Throwable ex,
                                         ServerWebExchange exchange) {
        HttpStatus status;
        String code;
        String message;

        if (ex instanceof WebExchangeBindException bindEx) {
            status  = HttpStatus.BAD_REQUEST;
            code    = "VALIDATION_ERROR";
            message = bindEx.getFieldErrors()
                    .stream()
                    .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                    .reduce((a,b) -> a + "; " + b)
                    .orElse(bindEx.getMessage());
        }
        else if (ex instanceof ApplicationException appEx) {
            status  = appEx.getStatus();
            code    = appEx.getCode();
            message = appEx.getMessage();
        }
        else {
            status  = HttpStatus.INTERNAL_SERVER_ERROR;
            code    = "INTERNAL_ERROR";
            message = "Ocurrió un error inesperado";
        }
        ErrorResponse err = ErrorResponse.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .code(code)
                .message(message)
                .path(exchange.getRequest().getPath().value())
                .build();

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return Mono.just(err);
    }
}