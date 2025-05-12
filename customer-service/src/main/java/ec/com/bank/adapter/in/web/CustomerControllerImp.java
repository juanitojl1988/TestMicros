package ec.com.bank.adapter.in.web;

import ec.com.bank.application.port.in.CreateCustomerUseCase;
import ec.com.bank.application.port.in.GetCustomerUseCase;
import ec.com.bank.application.port.in.UpdateCustomerUseCase;
import ec.com.bank.common.WebAdapter;
import ec.com.bank.domain.model.dto.CustomerCreateDto;
import ec.com.bank.domain.model.dto.CustomerDto;
import ec.com.bank.domain.model.dto.CustomerUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@WebAdapter
public class CustomerControllerImp implements CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;

    public CustomerControllerImp(CreateCustomerUseCase createCustomerUseCase, GetCustomerUseCase getCustomerUseCase, UpdateCustomerUseCase updateCustomerUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.getCustomerUseCase = getCustomerUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
    }


    @Override
    public Mono<ResponseEntity<CustomerDto>> getCostumerById(Long id) {
        return getCustomerUseCase.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<CustomerDto>> createCustomer(Mono<CustomerCreateDto> customerCreateDto) {
        return customerCreateDto
                .doOnNext(d -> log.info("Payload válido recibido: {}", d))
                .flatMap(dto -> createCustomerUseCase.create(dto)
                        .map(acc -> ResponseEntity.status(HttpStatus.CREATED).body(acc))
                        .defaultIfEmpty(ResponseEntity.notFound().build())
                );
    }

    @Override
    public Mono<ResponseEntity<CustomerDto>> updateCustomer(Long id, Mono<CustomerUpdateDto> customerUpdateDto) {
        return customerUpdateDto
                .doOnNext(d -> log.info("Payload válido recibido: {}", d))
                .flatMap(dto -> updateCustomerUseCase.update(id,dto)
                        .map(acc -> ResponseEntity.status(HttpStatus.OK).body(acc))
                        .defaultIfEmpty(ResponseEntity.notFound().build())
                );
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomerById(Long id) {
        return null;
    }
}
