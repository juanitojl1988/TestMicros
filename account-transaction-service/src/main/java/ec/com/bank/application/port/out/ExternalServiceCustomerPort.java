package ec.com.bank.application.port.out;

import ec.com.bank.domain.model.dto.CustomerDto;
import reactor.core.publisher.Mono;

public interface ExternalServiceCustomerPort {

    Mono<CustomerDto> getCustomerById(Long id);
}
