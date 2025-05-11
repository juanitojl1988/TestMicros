package ec.com.bank.adapter.out.persistence;

import ec.com.bank.adapter.out.persistence.entity.CustomerEntity;
import ec.com.bank.adapter.out.persistence.entity.PersonEntity;
import ec.com.bank.adapter.out.persistence.mapper.CustomerDboMapper;
import ec.com.bank.adapter.out.persistence.mapper.PersonDboMapper;
import ec.com.bank.application.port.out.CreateCustomerPort;
import ec.com.bank.application.port.out.GetCustomerPort;
import ec.com.bank.common.PersistenceAdapter;
import ec.com.bank.domain.exception.CustomerAlreadyExistsException;
import ec.com.bank.domain.exception.CustomerNotFoundException;
import ec.com.bank.domain.model.dto.CustomerCreateDto;
import ec.com.bank.domain.model.dto.CustomerDto;
import ec.com.bank.domain.model.dto.PersonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@PersistenceAdapter
public class JpaCustomerRepositoryAdapter implements GetCustomerPort, CreateCustomerPort {

    private final JpaCustomerRepository jpaCustomerRepository;
    private final JpaPersonRepository jpaPersonRepository;
    private final CustomerDboMapper customerDboMap;
    private final PersonDboMapper personDboMapper;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public JpaCustomerRepositoryAdapter(JpaCustomerRepository jpaCustomerRepository, JpaPersonRepository jpaPersonRepository, CustomerDboMapper customerDboMap, PersonDboMapper personDboMapper, R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.jpaCustomerRepository = jpaCustomerRepository;
        this.jpaPersonRepository = jpaPersonRepository;
        this.customerDboMap = customerDboMap;
        this.personDboMapper = personDboMapper;
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    @Transactional
    @Override
    public Mono<CustomerDto> create(CustomerCreateDto customerCreateDto) {
        PersonDto personDto = new PersonDto(customerCreateDto.getIdentification(), customerCreateDto.getName(), customerCreateDto.getGender(), customerCreateDto.getAge(), customerCreateDto.getAddresses(), customerCreateDto.getPhone());
        PersonEntity personEntity = personDboMapper.toDomain(personDto);
        CustomerEntity customerEntity = customerDboMap.toDomain1(customerCreateDto);

        return Mono.just(customerCreateDto).filterWhen(dto -> jpaPersonRepository.existsByIdentification(dto.getIdentification())
                        .map(exists -> !exists))
                .switchIfEmpty(Mono.error(new CustomerAlreadyExistsException(customerCreateDto.getIdentification())))
                .flatMap(dto -> jpaPersonRepository.save(personEntity)
                        .flatMap(savedPerson -> {
                            customerEntity.setId(savedPerson.getId());
                            return r2dbcEntityTemplate.insert(CustomerEntity.class).using(customerEntity);
                        }))
                .map(customerDboMap::toDbo).onErrorMap(e -> e instanceof CustomerAlreadyExistsException ? e : new RuntimeException("Error creando cliente: " + e.getMessage(), e));
    }

    @Override
    public Mono<CustomerDto> getById(Long id) {
        return Mono.zip(jpaPersonRepository.findById(id), jpaCustomerRepository.findById(id))
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(id.toString())))
                .doOnError(e -> log.error("Error al obtener customer con id {}: {}", id, e.getMessage(), e))
                .map(tuple -> {
                    PersonEntity person = tuple.getT1();
                    CustomerEntity customer = tuple.getT2();
                    CustomerDto dto = new CustomerDto();
                    dto.setId(person.getId());
                    dto.setIdentification(person.getIdentification());
                    dto.setName(person.getName());
                    dto.setGender(person.getGender());
                    dto.setAge(person.getAge());
                    dto.setAddresses(person.getAddresses());
                    dto.setPhone(person.getPhone());
                    dto.setState(customer.getState());
                    dto.setPassword(customer.getPassword());
                    log.debug("Customer con id {} obtenido: {}", id, dto);
                    return dto;
                });
    }

    @Override
    public Mono<CustomerDto> findByIdentification(String identification) {
        return null;
    }


}
