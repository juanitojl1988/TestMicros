package ec.com.bank.adapter.in.web;

import ec.com.bank.adapter.in.web.interfaces.AccountController;
import ec.com.bank.application.port.in.CreateAccountUseCase;
import ec.com.bank.common.WebAdapter;
import ec.com.bank.domain.model.dto.AccountCreateDto;
import ec.com.bank.domain.model.dto.AccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@WebAdapter
public class AccountControllerImp implements AccountController {

    private final CreateAccountUseCase createAccountUseCase;

    public AccountControllerImp(CreateAccountUseCase createAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
    }

    @Override
    public Mono<ResponseEntity<AccountDto>> getAccountById(Long id) {
        log.info("getAccountById: Id={}", id);
        return null;
    }

    @Override
    public Mono<ResponseEntity<AccountDto>> createAccount(Mono<AccountCreateDto> accountCreateDtoMono) {
        return accountCreateDtoMono
                .doOnNext(d -> log.info("Payload válido recibido: {}", d))
                .flatMap(dto -> createAccountUseCase.create(dto)
                        .map(acc -> ResponseEntity.status(HttpStatus.CREATED).body(acc))
                );
    }

    @Override
    public Mono<ResponseEntity<AccountDto>> updateAccount(Long id, AccountCreateDto accountCreateDto) {
        return null;
    }

    @Override
    public Mono<ResponseEntity> removeAccount(Long id) {
        return null;
    }
}
