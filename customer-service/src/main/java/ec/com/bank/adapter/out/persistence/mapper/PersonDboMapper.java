package ec.com.bank.adapter.out.persistence.mapper;

import ec.com.bank.adapter.out.persistence.entity.PersonEntity;
import ec.com.bank.domain.model.dto.PersonDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface  PersonDboMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "identification", target = "identification")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "addresses", target = "addresses")
    @Mapping(source = "phone", target = "phone")
    PersonDto toDbo(PersonEntity personEntity);

    @InheritInverseConfiguration
    PersonEntity toDomain(PersonDto personDto);
}
