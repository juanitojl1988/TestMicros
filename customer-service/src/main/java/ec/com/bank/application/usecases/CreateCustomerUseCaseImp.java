package ec.com.bank.application.usecases;

import ec.com.bank.application.port.in.CreateCustomerUseCase;
import ec.com.bank.application.port.out.CreateCustomerPort;
import ec.com.bank.common.UseCase;
import ec.com.bank.domain.model.dto.CustomerCreateDto;
import ec.com.bank.domain.model.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@UseCase
public class CreateCustomerUseCaseImp implements CreateCustomerUseCase {

    private final CreateCustomerPort createCustomerPort;


    public CreateCustomerUseCaseImp(CreateCustomerPort createCustomerPort) {
        this.createCustomerPort = createCustomerPort;

    }

    @Override
    public Mono<CustomerDto> create(CustomerCreateDto customerCreateDto) {
        log.info("Creacion de cliente con identificacion: {}", customerCreateDto.getIdentification());
        return createCustomerPort.create(customerCreateDto);
    }

}
