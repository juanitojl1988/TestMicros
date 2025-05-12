package ec.com.bank.adapter.out.persistence;

import ec.com.bank.domain.model.dto.TransactionWithAccountDto;
import reactor.core.publisher.Flux;

import java.time.Instant;

public interface TransactionRepositoryCustom {

    public Flux<TransactionWithAccountDto> findPageWithAccount(Long accountId, Instant from, Instant to, int size, long offset);
}
