package pe.mil.ejercito.microservice.services.contracts;

import com.bxcode.tools.loader.dto.PageableDto;
import com.bxcode.tools.loader.services.interfaces.*;
import pe.mil.ejercito.microservice.dtos.UnitDto;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * IUnitDomainService
 * <p>
 * IUnitDomainService interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 27/02/2024
 */
public interface IUnitDomainService extends
        IGetByIdDomainEntity<Mono<UnitDto>, Long>,
        IGetByUuIdDomainEntity<Mono<UnitDto>, String>,
        ISaveDomainEntity<Mono<UnitDto>, UnitDto>,
        IUpdateDomainEntity<Mono<UnitDto>, UnitDto>,
        IDeleteDomainEntity<Mono<UnitDto>,String> {
    Mono<List<UnitDto>> getAllEntities(Long brigadeId, Long statusId, String limit, String page, PageableDto pageable);
}