package pe.mil.ejercito.microservice.services.contracts;

import com.bxcode.tools.loader.services.interfaces.*;
import pe.mil.ejercito.microservice.dtos.BrigadeDto;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * IBrigadeDomainService
 * <p>
 * IBrigadeDomainService interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 27/02/2024
 */
public interface IBrigadeDomainService extends
        IGetAllDomainEntity<Mono<List<BrigadeDto>>>,
        IGetByIdDomainEntity<Mono<BrigadeDto>, Long>,
        IGetByUuIdDomainEntity<Mono<BrigadeDto>, String>,
        ISaveDomainEntity<Mono<BrigadeDto>, BrigadeDto>,
        IUpdateDomainEntity<Mono<BrigadeDto>, BrigadeDto>,
        IDeleteDomainEntity<Mono<BrigadeDto>,String> {
}