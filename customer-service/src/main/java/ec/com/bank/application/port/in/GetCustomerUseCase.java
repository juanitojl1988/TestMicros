package ec.com.bank.application.port.in;

import ec.com.bank.domain.model.dto.CustomerDto;
import reactor.core.publisher.Mono;

public interface GetCustomerUseCase {
    Mono<CustomerDto> getById(Long id);
}
