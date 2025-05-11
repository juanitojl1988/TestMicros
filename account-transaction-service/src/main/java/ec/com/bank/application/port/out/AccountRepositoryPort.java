package ec.com.bank.application.port.out;

import ec.com.bank.domain.model.dto.AccountDto;
import reactor.core.publisher.Mono;

public interface  AccountRepositoryPort {

    Mono<AccountDto> getById(Long id);
    Mono<AccountDto> create(AccountDto accountDto);
    Mono<Boolean> existAccountByNumAccount(String numAccount);
    Mono<AccountDto> findByNumberAccount(String numAccount);
}
