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
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bxcode.tools.loader.constants.BaseLoggerServicesConstant.*;

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

    private final IEpDivisionStatusRepository divisionStatusRepository;
    private final IDivisionStatusMapper divisionStatusMapper;

    public DivisionStatusDomainService(final IEpDivisionStatusRepository divisionStatusRepository,
                                       final IDivisionStatusMapper divisionStatusMapper) {
        super("DivisionStatusDomainService");
        this.divisionStatusRepository = divisionStatusRepository;
        this.divisionStatusMapper = divisionStatusMapper;
    }

    @Override
    public Mono<List<DivisionStatusDto>> getAllEntities() {
        final Iterable<EpDivisionStatusEntity> persistenceEntities = this.divisionStatusRepository.findAll();
        final List<DivisionStatusDto> list = this.divisionStatusMapper.mapperToList(persistenceEntities);
        return Mono.just(list)
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_ALL_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Transactional
    @Override
    public Mono<DivisionStatusDto> getByIdEntity(Long id) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidId(id))) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_ID));
        }

        final Optional<EpDivisionStatusEntity> persistenceEntity = this.divisionStatusRepository.findById(id);

        return getDivisionStatusDto(
                persistenceEntity,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_FORMAT_SUCCESS,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_NOT_EXIST_FORMAT_ERROR);
    }

    @Override
    public Mono<DivisionStatusDto> getByUuIdEntity(String uuId) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(uuId))) {
            log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_UUID));
        }

        final Optional<EpDivisionStatusEntity> persistenceEntity = this.divisionStatusRepository.findByUuId(uuId);

        return getDivisionStatusDto(
                persistenceEntity,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_FORMAT_SUCCESS,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
    }


    @Override
    public Mono<DivisionStatusDto> saveEntity(DivisionStatusDto dto) {
        return doOnSave(dto)
                .flatMap(this::doOnValidateResponse)
                .doOnSuccess(success -> log.info(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Override
    public Mono<DivisionStatusDto> updateEntity(DivisionStatusDto dto) {
        return doOnUpdate(dto)
                .flatMap(this::doOnValidateResponse)
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_UPDATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    private Mono<DivisionStatusDto> doOnValidateResponse(DivisionStatusDto dto) {
        return IDivisionStatusValidation.doOnValidationResponse().apply(dto)
                .flatMap(validation -> {
                    if (ProcessResult.PROCESS_FAILED.equals(validation.getProcessResult())) {
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_OR_UPDATE_VALIDATION_RESPONSE_FORMAT_ERROR, ResponseEnum.INTERNAL_SERVER_ERROR));
                    }
                    return Mono.just(dto);
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_OR_UPDATE_VALIDATION_RESPONSE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<DivisionStatusDto> doOnSave(DivisionStatusDto dto) {
        return Mono.just(dto)
                .flatMap(request -> {
                    final EpDivisionStatusEntity persistenceEntity = this.divisionStatusMapper.mapperToEntity(request);
                    persistenceEntity.setUuId(UUID.randomUUID().toString());
                    final EpDivisionStatusEntity entityResult = this.divisionStatusRepository.save(persistenceEntity);
                    return Mono.just(this.divisionStatusMapper.mapperToDto(entityResult));
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_SAVE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<DivisionStatusDto> doOnUpdate(DivisionStatusDto dto) {
        return Mono.just(dto)
                .flatMap(request -> {

                    if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(request.getUuId()))) {
                        log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_INVALID_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_UUID));
                    }

                    final Optional<EpDivisionStatusEntity> persistenceEntity = this.divisionStatusRepository.findByUuId(request.getUuId());
                    if (persistenceEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    final EpDivisionStatusEntity entityUpdate = persistenceEntity.get();
                    entityUpdate.setDsCode(request.getCode());
                    entityUpdate.setDsName(request.getName());
                    entityUpdate.setDsDescription(request.getDescription());
                    final EpDivisionStatusEntity entityResult = this.divisionStatusRepository.save(entityUpdate);
                    return Mono.just(this.divisionStatusMapper.mapperToDto(entityResult));
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    @Override
    public Mono<DivisionStatusDto> deleteByUuIdEntity(String uuId) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(uuId))) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_ID));
        }

        final Optional<EpDivisionStatusEntity> persistenceEntity = this.divisionStatusRepository.findByUuId(uuId);

        if (persistenceEntity.isEmpty()) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_NOT_EXIST_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
        }

        this.divisionStatusRepository.delete(persistenceEntity.get());
        return Mono.just(this.divisionStatusMapper.mapperToDto(persistenceEntity.get()))
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    private Mono<DivisionStatusDto> getDivisionStatusDto(Optional<EpDivisionStatusEntity> dsEntity, String successMessage, String messageExist) {
        return dsEntity.map(divisionStatus -> Mono.just(this.divisionStatusMapper.mapperToDto(divisionStatus))
                        .doOnSuccess(success -> log.debug(successMessage))
                        .doOnError(throwable -> log.error(throwable.getMessage())))
                .orElseGet(() -> Mono.error(() -> {
                    log.error(messageExist);
                    return new CommonException(messageExist, ResponseEnum.NOT_FOUNT_ENTITY);
                }));
    }
}