package pe.mil.ejercito.microservice.controllers.implementations;

import com.bxcode.tools.loader.controllers.base.ReactorControllerBase;
import com.bxcode.tools.loader.dto.GenericResponse;
import com.bxcode.tools.loader.dto.ProcessResponse;
import com.bxcode.tools.loader.dto.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import pe.mil.ejercito.microservice.controllers.contracts.IDivisionStatusController;
import pe.mil.ejercito.microservice.services.contracts.IDivisionStatusDomainService;
import reactor.core.publisher.Mono;

import static pe.mil.ejercito.microservice.constants.ProcessConstant.FIND_BY_ID_DIVISION_STATUS_PATH;
import static pe.mil.ejercito.microservice.constants.ProcessConstant.MICROSERVICE_PATH_CONTEXT;

/**
 * DivisionStatusController
 * <p>
 * DivisionStatusController class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 26/02/2024
 */

@Log4j2
@RestController
@RequestMapping(path = MICROSERVICE_PATH_CONTEXT, produces = MediaType.APPLICATION_JSON_VALUE)
public class DivisionStatusController extends ReactorControllerBase implements IDivisionStatusController {

    private final IDivisionStatusDomainService service;


    public DivisionStatusController(final Response response, IDivisionStatusDomainService service) {
        super(response, "DivisionStatusController");

        this.service = service;
    }

    @Override
    @GetMapping(path = FIND_BY_ID_DIVISION_STATUS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> doOnFindByIdExecute(@PathVariable(value = "statusId") Long id) {
        return this.service.getByIdEntity(id)
                .flatMap(current -> super.response(ProcessResponse.success(new GenericResponse<>(current))))
                .onErrorResume(WebExchangeBindException.class, Mono::error)
                .doOnSuccess(success -> log.info("success"))
                .doOnError(throwable -> log.error("error"));

    }
}