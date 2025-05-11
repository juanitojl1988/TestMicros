package ec.com.bank.adapter.in.web.util;

import ec.com.bank.domain.model.ErrorResponse;
import ec.com.bank.domain.model.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ErrorResponse> onValidationError(WebExchangeBindException ex,
                                                 ServerWebExchange exchange) {
        String msg = ex.getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return build(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", msg, exchange);
    }

    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ErrorResponse> onInputError(ServerWebInputException ex,
                                            ServerWebExchange exchange) {
        String message = ex.getCause() != null ? ex.getCause().toString() : "Petición mal formada";

        return build(HttpStatus.BAD_REQUEST,
                "VALIDATION_ERROR",
                message,
                exchange);
    }

    @ExceptionHandler(ApplicationException.class)
    public Mono<ErrorResponse> onAppError(ApplicationException ex,
                                          ServerWebExchange exchange) {
        return build(ex.getStatus(), ex.getCode(), ex.getMessage(), exchange);
    }

    @ExceptionHandler(Throwable.class)
    public Mono<ErrorResponse> onAnyError(Throwable ex,
                                          ServerWebExchange exchange) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_ERROR",
                "Ocurrió un error inesperado",
                exchange);
    }

    private Mono<ErrorResponse> build(HttpStatus status,
                                      String code,
                                      String message,
                                      ServerWebExchange ex) {
        ErrorResponse err = ErrorResponse.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .code(code)
                .message(message)
                .path(ex.getRequest().getPath().value())
                .build();

        ex.getResponse().setStatusCode(status);
        ex.getResponse().getHeaders()
                .setContentType(MediaType.APPLICATION_JSON);
        return Mono.just(err);
    }
}


