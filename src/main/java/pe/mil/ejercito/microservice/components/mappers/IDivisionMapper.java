package pe.mil.ejercito.microservice.components.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;
import pe.mil.ejercito.microservice.dtos.DivisionDto;
import pe.mil.ejercito.microservice.repositories.entities.EpDivisionEntity;

import java.util.List;

/**
 * IDivisionMapper
 * <p>
 * IDivisionMapper interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 27/02/2024
 */

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface IDivisionMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "diStatus.uuId", source = "status")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "diCreatedDate", source = "created")
    @Mapping(target = "diUpdatedDate", source = "updated")
    @Mapping(target = "epBrigades", ignore = true)
    EpDivisionEntity mapperToEntity(DivisionDto source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "status", source = "diStatus.uuId")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "created", source = "diCreatedDate")
    @Mapping(target = "updated", source = "diUpdatedDate")
    DivisionDto mapperToDto(EpDivisionEntity source);

    List<DivisionDto> mapperToList(Iterable<EpDivisionEntity> entities);
}