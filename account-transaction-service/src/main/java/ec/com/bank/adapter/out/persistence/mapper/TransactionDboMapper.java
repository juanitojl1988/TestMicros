package ec.com.bank.adapter.out.persistence.mapper;

import ec.com.bank.adapter.out.persistence.entity.AccountEntity;
import ec.com.bank.adapter.out.persistence.entity.TransactionEntity;
import ec.com.bank.domain.model.dto.TransactionCreateDto;
import ec.com.bank.domain.model.dto.TransactionDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionDboMapper {
    @Mapping(source = "id",               target = "id")
    @Mapping(source = "dateTransaction",  target = "dateTransaction")
    @Mapping(source = "typeTransaction",  target = "typeTransaction")
    @Mapping(source = "value",           target = "value")
    @Mapping(source = "balance",         target = "balance")
    @Mapping(source = "state",           target = "state")
    @Mapping(source = "accountId",       target = "accountId")
    @Mapping(source = "dateFrom",        target = "dateFrom")
    @Mapping(source = "dateTo",          target = "dateTo")
    TransactionDto toDbo(TransactionEntity transactionEntity);

    @InheritInverseConfiguration
    TransactionEntity toDomain(TransactionDto transactionDto);

    @InheritInverseConfiguration
    AccountEntity toDomain1(TransactionCreateDto accountCreateDto);

}
