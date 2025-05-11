package ec.com.bank.application.port.out;

import ec.com.bank.domain.model.dto.CustomerCreateDto;
import ec.com.bank.domain.model.dto.CustomerDto;
import reactor.core.publisher.Mono;

public interface CreateCustomerPort {

    Mono<CustomerDto> create(CustomerCreateDto customerCreateDto);


}
