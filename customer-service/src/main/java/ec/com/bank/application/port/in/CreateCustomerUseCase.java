package ec.com.bank.application.port.in;

import ec.com.bank.domain.model.dto.CustomerCreateDto;
import ec.com.bank.domain.model.dto.CustomerDto;
import reactor.core.publisher.Mono;

public interface CreateCustomerUseCase {

    Mono<CustomerDto> create(CustomerCreateDto customerCreateDto);
}
