package pe.mil.ejercito.microservice.services.contracts;

import com.bxcode.tools.loader.dto.PageableDto;
import com.bxcode.tools.loader.services.interfaces.*;
import pe.mil.ejercito.microservice.dtos.DivisionDto;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * IDivisionDomainService
 * <p>
 * IDivisionDomainService interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 27/02/2024
 */
public interface IDivisionDomainService extends
        IGetByIdDomainEntity<Mono<DivisionDto>, Long>,
        IGetByUuIdDomainEntity<Mono<DivisionDto>, String>,
        ISaveDomainEntity<Mono<DivisionDto>, DivisionDto>,
        IUpdateDomainEntity<Mono<DivisionDto>, DivisionDto>,
        IDeleteDomainEntity<Mono<DivisionDto>, String> {

    Mono<List<DivisionDto>> getAllEntities(Long statusId, String limit, String page, PageableDto pageable);
}