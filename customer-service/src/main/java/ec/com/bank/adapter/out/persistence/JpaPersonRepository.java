package ec.com.bank.adapter.out.persistence;

import ec.com.bank.adapter.out.persistence.entity.PersonEntity;
import ec.com.bank.domain.model.dto.CustomerDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface JpaPersonRepository extends ReactiveCrudRepository<PersonEntity, Long> {

    @Query("""
              SELECT p.id, p.identification, p.name
              FROM tperson p
              WHERE p.identification = :identification
            """)
    Mono<CustomerDto> findByIdentification(String identification);

    @Query("""
      SELECT EXISTS(
        SELECT 1
        FROM tperson p
        WHERE p.identification = :identification
      )
    """)
    Mono<Boolean> existsByIdentification(String identification);
}
