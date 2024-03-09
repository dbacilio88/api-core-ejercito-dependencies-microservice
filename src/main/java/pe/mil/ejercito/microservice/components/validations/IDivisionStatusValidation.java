package pe.mil.ejercito.microservice.components.validations;

import com.bxcode.tools.loader.componets.enums.ProcessResult;
import com.bxcode.tools.loader.componets.helpers.CommonRequestHelper;
import com.bxcode.tools.loader.componets.validations.ProcessValidationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.mil.ejercito.microservice.dtos.DivisionStatusDto;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static pe.mil.ejercito.microservice.constants.LoggerConstant.MICROSERVICE_PROCESS_VALIDATION_PARAMETER_REQUIRED;

/**
 * IDivisionStatusValidation
 * <p>
 * IDivisionStatusValidation interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 4/03/2024
 */

@FunctionalInterface
public interface IDivisionStatusValidation extends Function<DivisionStatusDto, Mono<ProcessValidationResult>> {
    Logger log = LogManager.getLogger(IDivisionStatusValidation.class);

    static IDivisionStatusValidation doOnValidationResponse() {
        return divisionStatusResponse -> {
            ProcessValidationResult validationResult = ProcessValidationResult.builder().processResult(ProcessResult.PROCESS_SUCCESS).build();
            List<String> errors = new ArrayList<>();

            if (Boolean.TRUE.equals(inValidDivisionStatusParameter(divisionStatusResponse))) {
                log.error(MICROSERVICE_PROCESS_VALIDATION_PARAMETER_REQUIRED);
                errors.add(MICROSERVICE_PROCESS_VALIDATION_PARAMETER_REQUIRED);
            }

            if (!errors.isEmpty()) {
                validationResult.setProcessResult(ProcessResult.PROCESS_FAILED);
                validationResult.setErrors(errors);
            }
            return Mono.just(validationResult);
        };
    }

    static boolean inValidDivisionStatusParameter(final DivisionStatusDto valid) {
        return Objects.isNull(valid.getId())
                || Objects.isNull(valid.getUuId())
                || CommonRequestHelper.isInvalidId(valid.getId())
                || CommonRequestHelper.isInvalidUuId(valid.getUuId())
                || Objects.isNull(valid.getName())
                || Objects.isNull(valid.getCode())
                || Objects.isNull(valid.getDescription());

    }
}