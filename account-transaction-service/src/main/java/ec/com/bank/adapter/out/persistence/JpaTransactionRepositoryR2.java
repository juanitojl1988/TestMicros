package ec.com.bank.adapter.out.persistence;

import ec.com.bank.domain.model.dto.TransactionWithAccountDto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.Instant;
@Repository
public interface JpaTransactionRepositoryR2 extends R2dbcRepository<TransactionWithAccountDto, Long>, TransactionRepositoryCustom {



}
