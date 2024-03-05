package pe.mil.ejercito.microservice.services.contracts;

import com.bxcode.tools.loader.services.interfaces.*;
import pe.mil.ejercito.microservice.dtos.DivisionStatusDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * IDivisionStatusDomainService
 * <p>
 * IDivisionStatusDomainService interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 27/02/2024
 */
public interface IDivisionStatusDomainService extends
        IGetAllDomainEntity<Flux<DivisionStatusDto>>,
        IGetByIdDomainEntity<Mono<DivisionStatusDto>, Long>,
        IGetByUuIdDomainEntity<Mono<DivisionStatusDto>, String>,
        ISaveDomainEntity<Mono<DivisionStatusDto>, DivisionStatusDto>,
        IUpdateDomainEntity<Mono<DivisionStatusDto>, DivisionStatusDto>,
        IDeleteDomainEntity<DivisionStatusDto> {
}