package ec.com.bank.application.port.in;

import ec.com.bank.domain.model.dto.AccountDto;
import reactor.core.publisher.Mono;

public interface GetAccountUseCase {
    Mono<AccountDto> getById(Long id);
}