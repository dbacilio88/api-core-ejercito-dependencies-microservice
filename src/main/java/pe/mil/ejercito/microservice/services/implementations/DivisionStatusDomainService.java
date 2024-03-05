package pe.mil.ejercito.microservice.services.implementations;

import com.bxcode.tools.loader.componets.enums.ProcessResult;
import com.bxcode.tools.loader.componets.enums.ResponseEnum;
import com.bxcode.tools.loader.componets.exceptions.CommonException;
import com.bxcode.tools.loader.componets.helpers.CommonRequestHelper;
import com.bxcode.tools.loader.services.base.ReactorServiceBase;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.mil.ejercito.microservice.components.mappers.IDivisionStatusMapper;
import pe.mil.ejercito.microservice.components.validations.IDivisionStatusValidation;
import pe.mil.ejercito.microservice.dtos.DivisionStatusDto;
import pe.mil.ejercito.microservice.repositories.IEpDivisionStatusRepository;
import pe.mil.ejercito.microservice.repositories.entities.EpDivisionStatusEntity;
import pe.mil.ejercito.microservice.services.contracts.IDivisionStatusDomainService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

import static com.bxcode.tools.loader.constants.BaseLoggerConstant.*;

/**
 * DivisionStatusDomainService
 * <p>
 * DivisionStatusDomainService class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 27/02/2024
 */
@Log4j2
@Service
public class DivisionStatusDomainService extends ReactorServiceBase implements IDivisionStatusDomainService {

    private final IEpDivisionStatusRepository repository;
    private final IDivisionStatusMapper mapper;

    public DivisionStatusDomainService(final IEpDivisionStatusRepository repository,
                                       final IDivisionStatusMapper mapper) {
        super("DivisionStatusDomainService");
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public Flux<DivisionStatusDto> getAllEntities() {
        return null;
    }

    @Transactional
    @Override
    public Mono<DivisionStatusDto> getByIdEntity(Long id) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidId(id))) {
            return Mono.error(() -> new CommonException(MICROSERVICE_COMMON_ERROR_GET_DOMAIN_ENTITY_FIND_BY_ID_INVALID_FORMAT, ResponseEnum.INTERNAL_SERVER_ERROR));
        }

        final Optional<EpDivisionStatusEntity> persistenceEntity = this.repository.findById(id);

        return persistenceEntity.map(epDivisionStatusEntity -> Mono.just(this.mapper.mapperToDto(epDivisionStatusEntity))
                .doOnSuccess(success -> log.debug(MICROSERVICE_COMMON_SUCCESS_GET_DOMAIN_ENTITY_FIND_BY_ID_FORMAT))
                .doOnError(throwable -> log.error(MICROSERVICE_COMMON_ERROR_GET_DOMAIN_ENTITY_FIND_BY_ID_FORMAT, throwable))
                .log()).orElseGet(() -> Mono.error(() -> new CommonException(MICROSERVICE_COMMON_ERROR_GET_DOMAIN_ENTITY_FIND_BY_ID_NOT_EXIST_FORMAT, ResponseEnum.INTERNAL_SERVER_ERROR)));
    }

    @Override
    public Mono<DivisionStatusDto> getByUuIdEntity(String uuId) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(uuId))) {
            return Mono.error(() -> new Exception(MICROSERVICE_COMMON_ERROR_GET_DOMAIN_ENTITY_FIND_BY_UUID_INVALID_FORMAT));
        }

        final Optional<EpDivisionStatusEntity> persistenceEntity = this.repository.findByUuId(uuId);

        return persistenceEntity.map(epDivisionStatusEntity -> Mono.just(this.mapper.mapperToDto(epDivisionStatusEntity))
                .doOnSuccess(success -> log.debug(MICROSERVICE_COMMON_SUCCESS_GET_DOMAIN_ENTITY_FIND_BY_UUID_FORMAT, success.toString()))
                .doOnError(throwable -> log.error(MICROSERVICE_COMMON_ERROR_GET_DOMAIN_ENTITY_FIND_BY_UUID_FORMAT, throwable))
                .log()).orElseGet(() -> Mono.error(() -> new Exception(MICROSERVICE_COMMON_ERROR_GET_DOMAIN_ENTITY_FIND_BY_UUID_NOT_EXIST_FORMAT)));
    }

    @Override
    public Mono<DivisionStatusDto> saveEntity(DivisionStatusDto dto) {
        return doOnValidateSaveRequest(dto)
                .flatMap(this::doOnSave)
                .flatMap(this::doOnValidateSaveResponse)
                .doOnSuccess(success -> log.info("saveEntity is success"))
                .doOnError(throwable -> log.error("saveEntity is error {}", throwable.getMessage()))
                .log();
    }

    @Override
    public Mono<DivisionStatusDto> updateEntity(DivisionStatusDto dto) {
        return doOnValidateSaveRequest(dto)
                .flatMap(this::doOnSave)
                .flatMap(this::doOnValidateSaveResponse)
                .doOnSuccess(success -> log.info("saveEntity is success"))
                .doOnError(throwable -> log.error("saveEntity is error {}", throwable.getMessage()))
                .log();
    }

    private Mono<DivisionStatusDto> doOnValidateSaveRequest(DivisionStatusDto dto) {
        return IDivisionStatusValidation.doValidationSaveRequest().apply(dto)
                .flatMap(validation -> {
                    if (ProcessResult.PROCESS_FAILED.equals(validation.getProcessResult())) {
                        return Mono.error(() -> new CommonException("error validation save request", ResponseEnum.INTERNAL_SERVER_ERROR));
                    }
                    return Mono.just(dto);
                }).doOnSuccess(success -> log.info("doOnValidateSaveRequest is success"))
                .doOnError(throwable -> log.error("doOnValidateSaveRequest is error {}", throwable.getMessage()))
                .log();
    }

    private Mono<DivisionStatusDto> doOnValidateSaveResponse(DivisionStatusDto dto) {
        return IDivisionStatusValidation.doValidationSaveResponse().apply(dto)
                .flatMap(validation -> {
                    if (ProcessResult.PROCESS_FAILED.equals(validation.getProcessResult())) {
                        return Mono.error(() -> new CommonException("error validation save response", ResponseEnum.INTERNAL_SERVER_ERROR));
                    }
                    return Mono.just(dto);
                }).doOnSuccess(success -> log.info("doOnValidateSaveResponse is success"))
                .doOnError(throwable -> log.error("doOnValidateSaveResponse is error {}", throwable.getMessage()))
                .log();
    }

    private Mono<DivisionStatusDto> doOnSave(DivisionStatusDto dto) {
        return Mono.just(dto)
                .flatMap(request -> {
                    final EpDivisionStatusEntity persistenceEntity = this.mapper.mapperToEntity(request);
                    persistenceEntity.setUuId(UUID.randomUUID().toString());
                    final EpDivisionStatusEntity entityResult = this.repository.save(persistenceEntity);
                    return Mono.just(this.mapper.mapperToDto(entityResult));
                }).doOnSuccess(success -> log.info("doOnSave is success"))
                .doOnError(throwable -> log.error("doOnSave is error {}", throwable.getMessage()))
                .log();
    }


    @Override
    public Boolean deleteEntity(DivisionStatusDto entity) {
        return null;
    }
}