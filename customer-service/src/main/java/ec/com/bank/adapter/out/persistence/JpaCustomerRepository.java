package ec.com.bank.adapter.out.persistence;

import ec.com.bank.adapter.out.persistence.entity.CustomerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface  JpaCustomerRepository extends ReactiveCrudRepository<CustomerEntity, Long> {


}
