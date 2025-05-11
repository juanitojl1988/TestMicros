package ec.com.bank.adapter.out.persistence;

import ec.com.bank.adapter.out.persistence.entity.TransactionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.Instant;

public interface JpaTransactionRepository extends ReactiveCrudRepository<TransactionEntity, Long> {

    Mono<TransactionEntity> findFirstByAccountIdAndDateTo(Long accountId, Instant dateTo);

    Mono<Long> countByAccountIdAndDateTransactionBetween(Long accountId, Instant dateFrom, Instant dateTo);




}
