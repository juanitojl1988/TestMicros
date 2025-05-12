package ec.com.bank.application.port.out;

import ec.com.bank.domain.model.dto.CustomerCreateDto;
import ec.com.bank.domain.model.dto.CustomerDto;
import ec.com.bank.domain.model.dto.CustomerUpdateDto;
import reactor.core.publisher.Mono;

public interface UpdateCustomerPort {

    Mono<CustomerDto> update(Long id,CustomerUpdateDto customerUpdateDto);


}
