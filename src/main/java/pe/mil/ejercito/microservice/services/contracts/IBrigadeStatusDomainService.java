package pe.mil.ejercito.microservice.services.contracts;

import com.bxcode.tools.loader.services.interfaces.*;
import pe.mil.ejercito.microservice.dtos.BrigadeStatusDto;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * IBrigadeStatusDomainService
 * <p>
 * IBrigadeStatusDomainService interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 27/02/2024
 */
public interface IBrigadeStatusDomainService extends
        IGetAllDomainEntity<Mono<List<BrigadeStatusDto>>>,
        IGetByIdDomainEntity<Mono<BrigadeStatusDto>, Long>,
        IGetByUuIdDomainEntity<Mono<BrigadeStatusDto>, String>,
        ISaveDomainEntity<Mono<BrigadeStatusDto>, BrigadeStatusDto>,
        IUpdateDomainEntity<Mono<BrigadeStatusDto>, BrigadeStatusDto>,
        IDeleteDomainEntity<Mono<BrigadeStatusDto>,String> {
}