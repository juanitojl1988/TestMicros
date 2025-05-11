package ec.com.bank.adapter.out.persistence;

import ec.com.bank.adapter.out.persistence.entity.AccountEntity;
import ec.com.bank.domain.model.enums.AccountConstant;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface  JpaAccountRepository extends ReactiveCrudRepository<AccountEntity, Long> {
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM taccount a WHERE a.number_account = :numberAccount AND a.state = 'A'")
    Mono<Boolean> existsByNumberAccountAndState(String numberAccount, AccountConstant.State state);

    @Query("SELECT a FROM taccount a WHERE a.number_account = :numberAccount")
    Mono<AccountEntity> findByNumberAccount(String numberAccount);
}
