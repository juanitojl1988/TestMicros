package ec.com.bank.adapter.out.persistence.mapper;

import ec.com.bank.adapter.out.persistence.entity.AccountEntity;
import ec.com.bank.domain.model.dto.AccountCreateDto;
import ec.com.bank.domain.model.dto.AccountDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface  AccountDboMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "numberAccount", target = "numberAccount")
    @Mapping(source = "typeAccount", target = "typeAccount")
    @Mapping(source = "initialBalance", target = "initialBalance")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "dateCreate", target = "dateCreate")
    AccountDto toDbo(AccountEntity accountEntity);

    @InheritInverseConfiguration
    AccountEntity toDomain(AccountDto accountDto);

    @InheritInverseConfiguration
    AccountEntity toDomain1(AccountCreateDto accountCreateDto);
}
