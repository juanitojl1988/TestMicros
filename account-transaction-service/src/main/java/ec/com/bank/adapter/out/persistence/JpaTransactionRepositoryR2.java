package ec.com.bank.adapter.out.persistence;

import ec.com.bank.adapter.out.persistence.entity.TransactionEntity;
import ec.com.bank.domain.model.dto.TransactionWithAccount;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.Instant;
@Repository
public interface JpaTransactionRepositoryR2 extends R2dbcRepository<TransactionEntity, Long> {

    @Query("""
        SELECT
          t.id                AS id,
          t.date_transaction  AS dateTransaction,
          t.type_transaction  AS typeTransaction,
          t.value             AS value,
          t.balance           AS balance,
          t.state             AS state,
          t.account_id        AS accountId,
          a.number_account    AS numberAccount,
          a.type_account      AS typeAccount,
          a.initial_balance   AS initialBalance,
          a.state             AS accountState,
          a.date_create       AS dateCreate
        FROM ttransaction t
        JOIN taccount a ON t.account_id = a.id
        WHERE t.account_id = :accountId
          AND t.date_transaction BETWEEN :from AND :to
        ORDER BY t.date_transaction DESC
        LIMIT :size OFFSET :offset
    """)
    Flux<TransactionWithAccount> findPageWithAccount(
            Long accountId,
            Instant from,
            Instant to,
            int size,
            long offset
    );


}
