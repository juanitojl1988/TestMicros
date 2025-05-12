package ec.com.bank.adapter.out.persistence;

import ec.com.bank.domain.model.dto.TransactionWithAccountDto;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.Instant;

@Repository
public class TransactionRepositoryCustomImpl implements TransactionRepositoryCustom {

    private final DatabaseClient db;

    public TransactionRepositoryCustomImpl(DatabaseClient db) {
        this.db = db;
    }
    @Override
    public Flux<TransactionWithAccountDto> findPageWithAccount(
            Long accountId,
            Instant from,
            Instant to,
            int size,
            long offset
    ) {
        String sql = """
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
                """;

        return db.sql(sql)
                .bind("accountId", accountId)
                .bind("from", from)
                .bind("to", to)
                .bind("size", size)
                .bind("offset", offset)
                .map((row, md) -> TransactionWithAccountDto.builder()
                        .id(row.get("id", Long.class))
                        .dateTransaction(row.get("dateTransaction", Instant.class))
                        .typeTransaction(row.get("typeTransaction", String.class))
                        .value(row.get("value", Double.class))
                        .balance(row.get("balance", BigDecimal.class))
                        .state(row.get("state", String.class))
                        .accountId(row.get("accountId", Long.class))
                        .numberAccount(row.get("numberAccount", String.class))
                        .typeAccount(row.get("typeAccount", String.class))
                        .initialBalance(row.get("initialBalance", BigDecimal.class))
                        .accountState(row.get("accountState", String.class))
                        .dateCreate(row.get("dateCreate", Instant.class))
                        .build()
                )
                .all();
    }
}


