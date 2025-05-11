package ec.com.bank.application.port.in;

import ec.com.bank.domain.model.dto.AccountCreateDto;
import ec.com.bank.domain.model.dto.AccountDto;
import reactor.core.publisher.Mono;

public interface CreateAccountUseCase {
    Mono<AccountDto> create(AccountCreateDto accountCreateDto);
}
