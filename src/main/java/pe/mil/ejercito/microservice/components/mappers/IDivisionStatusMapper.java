package pe.mil.ejercito.microservice.components.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;
import pe.mil.ejercito.microservice.dtos.DivisionStatusDto;
import pe.mil.ejercito.microservice.repositories.entities.EpDivisionStatusEntity;

import java.util.List;

/**
 * IDivisionStatusMapper
 * <p>
 * IDivisionStatusMapper interface.
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
public interface IDivisionStatusMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "dsCode", source = "code")
    @Mapping(target = "dsName", source = "name")
    @Mapping(target = "dsDescription", source = "description")
    @Mapping(target = "epDivisions", ignore = true)
    EpDivisionStatusEntity mapperToEntity(DivisionStatusDto source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "code", source = "dsName")
    @Mapping(target = "name", source = "dsName")
    @Mapping(target = "description", source = "dsDescription")
    DivisionStatusDto mapperToDto(EpDivisionStatusEntity source);

    List<DivisionStatusDto> mapperToList(Iterable<EpDivisionStatusEntity> entities);
}