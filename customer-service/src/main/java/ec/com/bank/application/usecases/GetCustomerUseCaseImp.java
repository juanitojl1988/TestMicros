package ec.com.bank.application.usecases;

import ec.com.bank.application.port.in.GetCustomerUseCase;
import ec.com.bank.application.port.out.GetCustomerPort;
import ec.com.bank.common.UseCase;
import ec.com.bank.domain.model.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@UseCase
public class GetCustomerUseCaseImp implements GetCustomerUseCase {

    private final GetCustomerPort getCustomerPort;

    public GetCustomerUseCaseImp( GetCustomerPort getCustomerPort) {
        this.getCustomerPort = getCustomerPort;
    }

    @Override
    public Mono<CustomerDto> getById(Long id) {
        log.info("Busqueda de cliente Id: {}", id);
        return this.getCustomerPort.getById(id);
    }
}
