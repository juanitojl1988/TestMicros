package ec.com.bank.adapter.out.integration.costumerservice;

import ec.com.bank.application.port.out.ExternalServiceCustomerPort;
import ec.com.bank.domain.model.dto.CustomerDto;
import ec.com.bank.domain.model.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class RestCustomerService implements ExternalServiceCustomerPort {

    private final WebClient webClient;


    public RestCustomerService(WebClient.Builder builder, @Value("${customer.service.url}") String baseUrl) {
        this.webClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public Mono<CustomerDto> getCustomerById(Long id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(s -> s == HttpStatus.NOT_FOUND,
                        resp -> Mono.error(new CustomerNotFoundException(id.toString())))
                .bodyToMono(CustomerDto.class)
                .doOnError(e -> log.error("Error fetching customer {}: {}", id, e.getMessage()));
    }
}
