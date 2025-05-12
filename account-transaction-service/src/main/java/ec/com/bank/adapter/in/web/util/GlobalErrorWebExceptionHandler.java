package ec.com.bank.adapter.in.web.util;

import ec.com.bank.domain.model.ErrorResponse;
import ec.com.bank.domain.model.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@Slf4j
@Component
@Order(-2)  // debe ejecutarse antes que el handler por defecto de Spring Boot
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        HttpStatus status;
        String code;
        String message;

        if (ex instanceof WebExchangeBindException bindEx) {
            status  = HttpStatus.BAD_REQUEST;
            code    = "VALIDATION_ERROR";
            message = bindEx.getFieldErrors().stream()
                    .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                    .collect(Collectors.joining("; "));
        }
        else if (ex instanceof ServerWebInputException inputEx) {
            status  = HttpStatus.BAD_REQUEST;
            code    = "VALIDATION_ERROR";
            message = inputEx.getCause() != null
                    ? inputEx.getCause().toString()
                    : "Petición mal formada";
        }
        else if (ex instanceof ApplicationException appEx) {
            status  = appEx.getStatus();
            code    = appEx.getCode();
            message = appEx.getMessage();
        }
        else {
            status  = HttpStatus.INTERNAL_SERVER_ERROR;
            code    = "INTERNAL_ERROR";
            message = ex.getMessage();
            log.error("Error no manejado", ex);
        }

        ErrorResponse err = ErrorResponse.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .code(code)
                .message(message)
                .path(exchange.getRequest().getPath().value())
                .build();

        DataBuffer bytes = exchange.getResponse()
                .bufferFactory()
                .wrap(ByteBuffer.wrap(err.toJson().getBytes(StandardCharsets.UTF_8)));

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return exchange.getResponse().writeWith(Mono.just(bytes));
    }
}

