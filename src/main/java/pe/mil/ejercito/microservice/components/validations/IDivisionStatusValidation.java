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

    static IDivisionStatusValidation doValidationSaveRequest() {
        return getiDivisionStatusValidation();
    }

    static IDivisionStatusValidation doValidationUpdateRequest() {
        return getiDivisionStatusValidation();
    }

    static IDivisionStatusValidation getiDivisionStatusValidation() {
        return request -> {
            ProcessValidationResult validationResult = ProcessValidationResult.builder().processResult(ProcessResult.PROCESS_SUCCESS).build();
            List<String> errors = new ArrayList<>();

            if (Boolean.TRUE.equals(inValidParameterRequest(request))) {
                log.error("error the field id is required");
                errors.add("error the field id is required");
            }

            if (!errors.isEmpty()) {
                validationResult.setProcessResult(ProcessResult.PROCESS_FAILED);
                validationResult.setErrors(errors);
            }
            return Mono.just(validationResult);
        };
    }

    static IDivisionStatusValidation doValidationSaveResponse() {
        return response -> {
            ProcessValidationResult validationResult = ProcessValidationResult.builder().processResult(ProcessResult.PROCESS_SUCCESS).build();
            List<String> errors = new ArrayList<>();

            if (Boolean.TRUE.equals(inValidParameterResponse(response))) {
                log.error("error the field id is required");
                errors.add("error the field id is required");
            }

            if (!errors.isEmpty()) {
                validationResult.setProcessResult(ProcessResult.PROCESS_FAILED);
                validationResult.setErrors(errors);
            }
            return Mono.just(validationResult);
        };
    }


    static boolean inValidParameterRequest(final DivisionStatusDto valid) {
        return Objects.isNull(valid.getCode())
                || Objects.isNull(valid.getDescription())
                || Objects.isNull(valid.getName());

    }

    static boolean inValidParameterResponse(final DivisionStatusDto valid) {
        return CommonRequestHelper.isInvalidId(valid.getId())
                || Objects.isNull(valid.getUuId())
                || Objects.isNull(valid.getName())
                || Objects.isNull(valid.getCode())
                || Objects.isNull(valid.getDescription());

    }
}