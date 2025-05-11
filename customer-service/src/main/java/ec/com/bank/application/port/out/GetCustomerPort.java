package ec.com.bank.application.port.out;

import ec.com.bank.domain.model.dto.CustomerDto;
import reactor.core.publisher.Mono;

public interface GetCustomerPort {

    Mono<CustomerDto> getById(Long id);
    Mono<CustomerDto> findByIdentification(String identification);

}
