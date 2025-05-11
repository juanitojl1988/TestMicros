package ec.com.bank.adapter.out.persistence.mapper;

import ec.com.bank.adapter.out.persistence.entity.CustomerEntity;
import ec.com.bank.domain.model.dto.CustomerCreateDto;
import ec.com.bank.domain.model.dto.CustomerDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface  CustomerDboMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "state", target = "state")
    @Mapping(source = "password", target = "password")
    CustomerDto toDbo(CustomerEntity customerEntity);

    @InheritInverseConfiguration
    CustomerEntity toDomain(CustomerDto customerDto);

    @InheritInverseConfiguration
    CustomerEntity toDomain1(CustomerCreateDto customerCreateDto);

}
