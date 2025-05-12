package ec.com.bank.application.usecases;

import ec.com.bank.application.port.in.UpdateCustomerUseCase;
import ec.com.bank.application.port.out.UpdateCustomerPort;
import ec.com.bank.common.UseCase;
import ec.com.bank.domain.model.dto.CustomerDto;
import ec.com.bank.domain.model.dto.CustomerUpdateDto;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@UseCase
public class UpdateCustomerUseCaseImpl implements UpdateCustomerUseCase {

    private final UpdateCustomerPort updateCustomerPort;

    public UpdateCustomerUseCaseImpl(UpdateCustomerPort updateCustomerPort) {
        this.updateCustomerPort = updateCustomerPort;
    }

    @Override
    public Mono<CustomerDto> update(Long id,CustomerUpdateDto customerUpdateDto) {
        return updateCustomerPort.update( id,customerUpdateDto);
    }
}
