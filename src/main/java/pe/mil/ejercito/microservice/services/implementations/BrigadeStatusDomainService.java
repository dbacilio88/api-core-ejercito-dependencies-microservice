package pe.mil.ejercito.microservice.services.implementations;

import com.bxcode.tools.loader.componets.enums.ProcessResult;
import com.bxcode.tools.loader.componets.enums.ResponseEnum;
import com.bxcode.tools.loader.componets.exceptions.CommonException;
import com.bxcode.tools.loader.componets.helpers.CommonRequestHelper;
import com.bxcode.tools.loader.services.base.ReactorServiceBase;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.mil.ejercito.microservice.components.mappers.IBrigadeStatusMapper;
import pe.mil.ejercito.microservice.components.validations.IBrigadeStatusValidation;
import pe.mil.ejercito.microservice.dtos.BrigadeStatusDto;
import pe.mil.ejercito.microservice.repositories.IEpBrigadeStatusRepository;
import pe.mil.ejercito.microservice.repositories.entities.EpBrigadeStatusEntity;
import pe.mil.ejercito.microservice.services.contracts.IBrigadeStatusDomainService;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bxcode.tools.loader.constants.BaseLoggerServicesConstant.*;

/**
 * BrigadeStatusDomainService
 * <p>
 * BrigadeStatusDomainService class.
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
public class BrigadeStatusDomainService extends ReactorServiceBase implements IBrigadeStatusDomainService {

    private final IEpBrigadeStatusRepository brigadeStatusRepository;
    private final IBrigadeStatusMapper brigadeStatusMapper;

    public BrigadeStatusDomainService(final IEpBrigadeStatusRepository brigadeStatusRepository,
                                      final IBrigadeStatusMapper brigadeStatusMapper) {
        super("BrigadeStatusDomainService");
        this.brigadeStatusRepository = brigadeStatusRepository;
        this.brigadeStatusMapper = brigadeStatusMapper;
    }

    @Override
    public Mono<List<BrigadeStatusDto>> getAllEntities() {
        final Iterable<EpBrigadeStatusEntity> persistenceEntities = this.brigadeStatusRepository.findAll();
        final List<BrigadeStatusDto> list = this.brigadeStatusMapper.mapperToList(persistenceEntities);
        return Mono.just(list)
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_ALL_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Transactional
    @Override
    public Mono<BrigadeStatusDto> getByIdEntity(Long id) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidId(id))) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_ID));
        }

        final Optional<EpBrigadeStatusEntity> persistenceEntity = this.brigadeStatusRepository.findById(id);

        return getBrigadeStatusDto(
                persistenceEntity,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_FORMAT_SUCCESS,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_NOT_EXIST_FORMAT_ERROR);
    }

    @Override
    public Mono<BrigadeStatusDto> getByUuIdEntity(String uuId) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(uuId))) {
            log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_UUID));
        }

        final Optional<EpBrigadeStatusEntity> persistenceEntity = this.brigadeStatusRepository.findByUuId(uuId);

        return getBrigadeStatusDto(
                persistenceEntity,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_FORMAT_SUCCESS,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
    }


    @Override
    public Mono<BrigadeStatusDto> saveEntity(BrigadeStatusDto dto) {
        return doOnSave(dto)
                .flatMap(this::doOnValidateResponse)
                .doOnSuccess(success -> log.info(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Override
    public Mono<BrigadeStatusDto> updateEntity(BrigadeStatusDto dto) {
        return doOnUpdate(dto)
                .flatMap(this::doOnValidateResponse)
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_UPDATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    private Mono<BrigadeStatusDto> doOnValidateResponse(BrigadeStatusDto dto) {
        return IBrigadeStatusValidation.doOnValidationResponse().apply(dto)
                .flatMap(validation -> {
                    if (ProcessResult.PROCESS_FAILED.equals(validation.getProcessResult())) {
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_OR_UPDATE_VALIDATION_RESPONSE_FORMAT_ERROR, ResponseEnum.INTERNAL_SERVER_ERROR));
                    }
                    return Mono.just(dto);
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_OR_UPDATE_VALIDATION_RESPONSE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<BrigadeStatusDto> doOnSave(BrigadeStatusDto dto) {
        return Mono.just(dto)
                .flatMap(request -> {
                    final EpBrigadeStatusEntity persistenceEntity = this.brigadeStatusMapper.mapperToEntity(request);
                    persistenceEntity.setUuId(UUID.randomUUID().toString());
                    final EpBrigadeStatusEntity entityResult = this.brigadeStatusRepository.save(persistenceEntity);
                    return Mono.just(this.brigadeStatusMapper.mapperToDto(entityResult));
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_SAVE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<BrigadeStatusDto> doOnUpdate(BrigadeStatusDto dto) {
        return Mono.just(dto)
                .flatMap(request -> {

                    if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(request.getUuId()))) {
                        log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_INVALID_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_UUID));
                    }

                    final Optional<EpBrigadeStatusEntity> persistenceEntity = this.brigadeStatusRepository.findByUuId(request.getUuId());
                    if (persistenceEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    final EpBrigadeStatusEntity entityUpdate = persistenceEntity.get();
                    entityUpdate.setBsCode(request.getCode());
                    entityUpdate.setBsName(request.getName());
                    entityUpdate.setBsDescription(request.getDescription());
                    final EpBrigadeStatusEntity entityResult = this.brigadeStatusRepository.save(entityUpdate);
                    return Mono.just(this.brigadeStatusMapper.mapperToDto(entityResult));
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    @Override
    public Mono<BrigadeStatusDto> deleteByUuIdEntity(String uuId) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(uuId))) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_ID));
        }

        final Optional<EpBrigadeStatusEntity> persistenceEntity = this.brigadeStatusRepository.findByUuId(uuId);

        if (persistenceEntity.isEmpty()) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_NOT_EXIST_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
        }

        this.brigadeStatusRepository.delete(persistenceEntity.get());
        return Mono.just(this.brigadeStatusMapper.mapperToDto(persistenceEntity.get()))
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    private Mono<BrigadeStatusDto> getBrigadeStatusDto(Optional<EpBrigadeStatusEntity> bsEntity, String successMessage, String messageExist) {
        return bsEntity.map(brigadeStatus -> Mono.just(this.brigadeStatusMapper.mapperToDto(brigadeStatus))
                        .doOnSuccess(success -> log.debug(successMessage))
                        .doOnError(throwable -> log.error(throwable.getMessage())))
                .orElseGet(() -> Mono.error(() -> {
                    log.error(messageExist);
                    return new CommonException(messageExist, ResponseEnum.NOT_FOUNT_ENTITY);
                }));
    }
}