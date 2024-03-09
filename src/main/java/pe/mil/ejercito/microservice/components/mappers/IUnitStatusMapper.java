package pe.mil.ejercito.microservice.components.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;
import pe.mil.ejercito.microservice.dtos.UnitStatusDto;
import pe.mil.ejercito.microservice.repositories.entities.EpUnitStatusEntity;

import java.util.List;

/**
 * IUnitStatusMapper
 * <p>
 * IUnitStatusMapper interface.
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
public interface IUnitStatusMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "usCode", source = "code")
    @Mapping(target = "usName", source = "name")
    @Mapping(target = "usDescription", source = "description")
    @Mapping(target = "epUnits", ignore = true)
    EpUnitStatusEntity mapperToEntity(UnitStatusDto source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "code", source = "usCode")
    @Mapping(target = "name", source = "usName")
    @Mapping(target = "description", source = "usDescription")
    UnitStatusDto mapperToDto(EpUnitStatusEntity source);

    List<UnitStatusDto> mapperToList(Iterable<EpUnitStatusEntity> entities);
}