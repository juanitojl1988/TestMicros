package ec.com.bank.adapter.in.web;

import ec.com.bank.application.port.in.CreateCustomerUseCase;
import ec.com.bank.application.port.in.GetCustomerUseCase;
import ec.com.bank.common.WebAdapter;
import ec.com.bank.domain.model.dto.CustomerCreateDto;
import ec.com.bank.domain.model.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@WebAdapter
public class CustomerControllerImp implements CustomerController{

    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;


    public CustomerControllerImp(CreateCustomerUseCase createCustomerUseCase, GetCustomerUseCase getCustomerUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.getCustomerUseCase = getCustomerUseCase;
    }


    @Override
    public Mono<ResponseEntity<CustomerDto>> getCostumerById(Long id) {
        return getCustomerUseCase.getById(id).map(customer -> {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        });
    }

    @Override
    public Mono<ResponseEntity<CustomerDto>> createCustomer(CustomerCreateDto customerCreateDto) {
        return createCustomerUseCase.create(customerCreateDto).map(customerDto -> {
            return new ResponseEntity<>(customerDto, HttpStatus.CREATED);
        });
    }
}
