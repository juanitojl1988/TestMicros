package ec.com.bank.adapter.out.persistence;

import ec.com.bank.adapter.out.persistence.entity.TransactionEntity;
import ec.com.bank.adapter.out.persistence.mapper.AccountDboMapper;
import ec.com.bank.adapter.out.persistence.mapper.TransactionDboMapper;
import ec.com.bank.application.port.out.TransactionRepositoryPort;
import ec.com.bank.common.PersistenceAdapter;
import ec.com.bank.domain.model.dto.PaginatedResponseDto;
import ec.com.bank.domain.model.dto.TransactionDto;
import ec.com.bank.domain.model.dto.TransactionWithAccountDto;
import ec.com.bank.domain.model.exception.TransactionCreationException;
import ec.com.bank.domain.model.util.FunctionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;

@Slf4j
@PersistenceAdapter
public class JpaTransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final JpaTransactionRepository jpaTransactionRepository;
    private final AccountDboMapper accountDboMapper;
    private final TransactionDboMapper transactionDboMapper;
    private final JpaTransactionRepositoryR2 jpaTransactionRepositoryR2;

    public JpaTransactionRepositoryAdapter(JpaTransactionRepository jpaTransactionRepository, AccountDboMapper accountDboMapper, TransactionDboMapper transactionDboMapper, JpaTransactionRepositoryR2 jpaTransactionRepositoryR2) {
        this.jpaTransactionRepository = jpaTransactionRepository;
        this.accountDboMapper = accountDboMapper;
        this.transactionDboMapper = transactionDboMapper;
        this.jpaTransactionRepositoryR2 = jpaTransactionRepositoryR2;
    }

    @Override
    public Mono<TransactionDto> getById(Long id) {
        return null;
    }

    @Transactional
    @Override
    public Mono<TransactionDto> create(TransactionDto transactionDto) {
        log.info("Iniciando transacción para cuenta {}", transactionDto.getAccountId());
        return Mono.defer(() -> {
            TransactionEntity lastTransaction = transactionDboMapper.toDomain(transactionDto.getLastTransaction());
            lastTransaction.setDateTo(FunctionUtils.now());
            TransactionEntity newTransaction = transactionDboMapper.toDomain(transactionDto);
            return jpaTransactionRepository.save(lastTransaction).then(jpaTransactionRepository.save(newTransaction));
        }).map(transactionDboMapper::toDbo).doOnSuccess(t -> log.info("Transacción creada id={} balance={}", t.getId(), t.getBalance())).doOnError(e -> log.error("Fallo en transacción cuenta {}: {}", transactionDto.getAccountId(), e.getMessage(), e)).onErrorMap(e -> new TransactionCreationException("Error creando transacción"));
    }

    @Override
    public Mono<TransactionDto> findFirstByAccountIdAndDateTo(Long idAccount) {
        return jpaTransactionRepository.findFirstByAccountIdAndDateTo(idAccount, FunctionUtils.maxDate()).map(transactionDboMapper::toDbo);
    }

    @Override
    public Mono<PaginatedResponseDto<TransactionWithAccountDto>> findAllTransactionsForDatesAndCustomer(String dateStart, String dateEnd, Long customerId, int page, int size) {
        long offset = (long) page * size;
        Instant start = FunctionUtils.parseDateDdMmYyyy(dateStart);
        Instant end = FunctionUtils.parseDateDdMmYyyy(dateEnd);

        Mono<Long> totalCount = jpaTransactionRepository.countByAccountIdAndDateTransactionBetween(customerId, start, end);

        Mono<List<TransactionWithAccountDto>> content = jpaTransactionRepositoryR2.findPageWithAccount(customerId, start, end, size, offset)
                .collectList();

        return Mono.zip(totalCount, content).map(tuple -> {
            Long total = tuple.getT1();
            List<TransactionWithAccountDto> list = tuple.getT2();

            return new PaginatedResponseDto<>(list, page, size, total);
        });
    }
}
