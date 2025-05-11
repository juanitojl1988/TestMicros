package ec.com.bank.adapter.out.persistence;

import ec.com.bank.adapter.out.persistence.entity.AccountEntity;
import ec.com.bank.adapter.out.persistence.entity.TransactionEntity;
import ec.com.bank.adapter.out.persistence.mapper.AccountDboMapper;
import ec.com.bank.adapter.out.persistence.mapper.TransactionDboMapper;
import ec.com.bank.application.port.out.AccountRepositoryPort;
import ec.com.bank.common.PersistenceAdapter;
import ec.com.bank.domain.model.dto.AccountCreateDto;
import ec.com.bank.domain.model.dto.AccountDto;
import ec.com.bank.domain.model.dto.TransactionDto;
import ec.com.bank.domain.model.enums.AccountConstant;
import ec.com.bank.domain.model.exception.AccountNotCreateException;
import ec.com.bank.domain.model.exception.AccountNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;



@Slf4j
@PersistenceAdapter
public class JpaAccountRepositoryAdapter implements AccountRepositoryPort {

    private final JpaAccountRepository jpaAccountRepository;
    private final AccountDboMapper accountDboMapper;
    private final JpaTransactionRepository transactionRepository;
    private final TransactionDboMapper transactionDboMapper;

    public JpaAccountRepositoryAdapter(JpaAccountRepository jpaAccountRepository, AccountDboMapper accountDboMapper, JpaTransactionRepository transactionRepository, TransactionDboMapper transactionDboMapper) {
        this.jpaAccountRepository = jpaAccountRepository;
        this.accountDboMapper = accountDboMapper;
        this.transactionRepository = transactionRepository;
        this.transactionDboMapper = transactionDboMapper;
    }


    @Override
    public Mono<AccountDto> getById(Long id) {
        return jpaAccountRepository.findById(id)
                .flatMap(accountE -> {
                    return Mono.just(accountDboMapper.toDbo(accountE));
                }).switchIfEmpty(Mono.error(new AccountNotFoundException(id.toString())));
    }

    @Transactional
    @Override
    public Mono<AccountDto> create(AccountDto accountDto) {
        AccountEntity accountEntity = accountDboMapper.toDomain(accountDto);
        TransactionEntity transactionEntity = transactionDboMapper.toDomain(accountDto.getTransactionInitDto());

        return jpaAccountRepository.save(accountEntity)
                .flatMap(saved -> {
                    log.info("Cuenta creada id={}", saved.getId());
                    transactionEntity.setAccountId(saved.getId());
                    return transactionRepository.save(transactionEntity)
                            .doOnSuccess(t -> log.info("Transacción inicial {} guardada", t.getId()))
                            .thenReturn(saved);
                })
                .map(accountDboMapper::toDbo)
                .doOnError(e -> log.error("Error creando cuenta/transacción: {}", e.getMessage(), e))
                .onErrorMap(e -> new AccountNotCreateException("No se pudo crear la cuenta"));
    }

    @Override
    public Mono<Boolean> existAccountByNumAccount(String numAccount) {
        return jpaAccountRepository.existsByNumberAccountAndState(numAccount, AccountConstant.State.A);
    }

    @Override
    public Mono<AccountDto> findByNumberAccount(String numAccount) {
        return jpaAccountRepository.findByNumberAccount(numAccount).map(
                accountDboMapper::toDbo
        );
    }
}
