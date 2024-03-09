package pe.mil.ejercito.microservice.services.contracts;

import com.bxcode.tools.loader.services.interfaces.*;
import pe.mil.ejercito.microservice.dtos.UnitStatusDto;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * IUnitStatusDomainService
 * <p>
 * IUnitStatusDomainService interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 27/02/2024
 */
public interface IUnitStatusDomainService extends
        IGetAllDomainEntity<Mono<List<UnitStatusDto>>>,
        IGetByIdDomainEntity<Mono<UnitStatusDto>, Long>,
        IGetByUuIdDomainEntity<Mono<UnitStatusDto>, String>,
        ISaveDomainEntity<Mono<UnitStatusDto>, UnitStatusDto>,
        IUpdateDomainEntity<Mono<UnitStatusDto>, UnitStatusDto>,
        IDeleteDomainEntity<Mono<UnitStatusDto>,String> {
}