package ec.com.bank.application.service;

import ec.com.bank.adapter.out.persistence.entity.CustomerEntity;
import ec.com.bank.application.port.in.CreateCustomerUseCase;
import ec.com.bank.application.port.in.GetCustomerUseCase;
import ec.com.bank.application.port.out.CreateCustomerPort;
import ec.com.bank.application.port.out.GetCustomerPort;
import ec.com.bank.common.UseCase;
import ec.com.bank.domain.exception.CustomerAlreadyExistsException;
import ec.com.bank.domain.model.dto.CustomerCreateDto;
import ec.com.bank.domain.model.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@UseCase
public class CustomerService implements CreateCustomerUseCase, GetCustomerUseCase {

    private final CreateCustomerPort createCustomerPort;
    private final GetCustomerPort getCustomerPort;

    public CustomerService(CreateCustomerPort createCustomerPort, GetCustomerPort getCustomerPort) {
        this.createCustomerPort = createCustomerPort;
        this.getCustomerPort = getCustomerPort;
    }

    @Override
    public Mono<CustomerDto> create(CustomerCreateDto customerCreateDto) {
        log.info("Creacion de cliente con identificacion: {}", customerCreateDto.getIdentification());
        return createCustomerPort.create(customerCreateDto);
    }

    @Override
    public Mono<CustomerDto> getById(Long id) {
        log.info("Busqueda de cliente Id: {}", id);
        return this.getCustomerPort.getById(id);
    }
}
