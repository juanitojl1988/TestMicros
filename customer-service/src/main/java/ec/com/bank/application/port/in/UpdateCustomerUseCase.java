package ec.com.bank.application.port.in;

import ec.com.bank.domain.model.dto.CustomerDto;
import ec.com.bank.domain.model.dto.CustomerUpdateDto;
import reactor.core.publisher.Mono;

public interface UpdateCustomerUseCase {

    Mono<CustomerDto> update(Long id,CustomerUpdateDto customerUpdateDto);


}
