package pe.mil.ejercito.microservice.components.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;
import pe.mil.ejercito.microservice.dtos.UnitDto;
import pe.mil.ejercito.microservice.repositories.entities.EpUnitEntity;

import java.util.List;

/**
 * IUnitMapper
 * <p>
 * IUnitMapper interface.
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
public interface IUnitMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "unBrigade.uuId", source = "brigade")
    @Mapping(target = "unCode", source = "code")
    @Mapping(target = "unName", source = "name")
    @Mapping(target = "unDescription", source = "description")
    @Mapping(target = "unCreatedDate", source = "created")
    @Mapping(target = "unUpdatedDate", source = "updated")
    @Mapping(target = "unStatus.uuId", source = "status")
    EpUnitEntity mapperToEntity(UnitDto source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "uuId", source = "uuId")
    @Mapping(target = "status", source = "unStatus.uuId")
    @Mapping(target = "code", source = "unCode")
    @Mapping(target = "name", source = "unName")
    @Mapping(target = "description", source = "unDescription")
    @Mapping(target = "created", source = "unCreatedDate")
    @Mapping(target = "updated", source = "unUpdatedDate")
    @Mapping(target = "brigade", source = "unBrigade.uuId")
    UnitDto mapperToDto(EpUnitEntity source);

    List<UnitDto> mapperToList(Iterable<EpUnitEntity> entities);
}