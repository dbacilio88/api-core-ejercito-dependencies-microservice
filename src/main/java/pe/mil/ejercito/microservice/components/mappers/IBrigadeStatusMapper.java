package pe.mil.ejercito.microservice.components.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;
import pe.mil.ejercito.microservice.dtos.BrigadeStatusDto;
import pe.mil.ejercito.microservice.repositories.entities.EpBrigadeStatusEntity;

import java.util.List;

/**
 * IBrigadeStatusMapper
 * <p>
 * IBrigadeStatusMapper interface.
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
public interface IBrigadeStatusMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "bsCode", source = "code")
    @Mapping(target = "bsName", source = "name")
    @Mapping(target = "bsDescription", source = "description")
    @Mapping(target = "epBrigades", ignore = true)
    EpBrigadeStatusEntity mapperToEntity(BrigadeStatusDto source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "code", source = "bsCode")
    @Mapping(target = "name", source = "bsName")
    @Mapping(target = "description", source = "bsDescription")
    BrigadeStatusDto mapperToDto(EpBrigadeStatusEntity source);

    List<BrigadeStatusDto> mapperToList(Iterable<EpBrigadeStatusEntity> entities);
}