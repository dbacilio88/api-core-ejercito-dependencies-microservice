package pe.mil.ejercito.microservice.services.implementations;

import com.bxcode.tools.loader.componets.enums.ProcessResult;
import com.bxcode.tools.loader.componets.enums.ResponseEnum;
import com.bxcode.tools.loader.componets.exceptions.CommonException;
import com.bxcode.tools.loader.componets.helpers.CommonRequestHelper;
import com.bxcode.tools.loader.services.base.ReactorServiceBase;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.mil.ejercito.microservice.components.mappers.IBrigadeMapper;
import pe.mil.ejercito.microservice.components.validations.IBrigadeValidation;
import pe.mil.ejercito.microservice.dtos.BrigadeDto;
import pe.mil.ejercito.microservice.repositories.IEpBrigadeRepository;
import pe.mil.ejercito.microservice.repositories.IEpBrigadeStatusRepository;
import pe.mil.ejercito.microservice.repositories.IEpDivisionRepository;
import pe.mil.ejercito.microservice.repositories.entities.EpBrigadeEntity;
import pe.mil.ejercito.microservice.repositories.entities.EpBrigadeStatusEntity;
import pe.mil.ejercito.microservice.repositories.entities.EpDivisionEntity;
import pe.mil.ejercito.microservice.services.contracts.IBrigadeDomainService;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bxcode.tools.loader.constants.BaseLoggerServicesConstant.*;

/**
 * BrigadeDomainService
 * <p>
 * BrigadeDomainService class.
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
public class BrigadeDomainService extends ReactorServiceBase implements IBrigadeDomainService {

    private final IEpBrigadeRepository repository;
    private final IEpBrigadeStatusRepository statusRepository;
    private final IEpDivisionRepository divisionRepository;
    private final IBrigadeMapper mapper;

    public BrigadeDomainService(final IEpBrigadeRepository repository,
                                final IEpBrigadeStatusRepository statusRepository,
                                final IEpDivisionRepository divisionRepository,
                                final IBrigadeMapper mapper) {
        super("BrigadeDomainService");
        this.repository = repository;
        this.statusRepository = statusRepository;
        this.divisionRepository = divisionRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<List<BrigadeDto>> getAllEntities() {
        final Iterable<EpBrigadeEntity> persistenceEntities = this.repository.findAllCustom();
        final List<BrigadeDto> list = this.mapper.mapperToList(persistenceEntities);
        return Mono.just(list)
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_ALL_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Transactional
    @Override
    public Mono<BrigadeDto> getByIdEntity(Long id) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidId(id))) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_ID));
        }

        final Optional<EpBrigadeEntity> persistenceEntity = this.repository.findById(id);

        return getBrigadeDto(
                persistenceEntity,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_FORMAT_SUCCESS,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_NOT_EXIST_FORMAT_ERROR);
    }

    @Override
    public Mono<BrigadeDto> getByUuIdEntity(String uuId) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(uuId))) {
            log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_UUID));
        }

        final Optional<EpBrigadeEntity> persistenceEntity = this.repository.findByUuId(uuId);

        return getBrigadeDto(
                persistenceEntity,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_FORMAT_SUCCESS,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
    }


    @Override
    public Mono<BrigadeDto> saveEntity(BrigadeDto dto) {
        return doOnSave(dto)
                .flatMap(this::doOnValidateResponse)
                .doOnSuccess(success -> log.info(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Override
    public Mono<BrigadeDto> updateEntity(BrigadeDto dto) {
        return doOnUpdate(dto)
                .flatMap(this::doOnValidateResponse)
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_UPDATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    private Mono<BrigadeDto> doOnValidateResponse(BrigadeDto dto) {
        return IBrigadeValidation.doOnValidationResponse().apply(dto)
                .flatMap(validation -> {
                    if (ProcessResult.PROCESS_FAILED.equals(validation.getProcessResult())) {
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_OR_UPDATE_VALIDATION_RESPONSE_FORMAT_ERROR, ResponseEnum.INTERNAL_SERVER_ERROR));
                    }
                    return Mono.just(dto);
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_OR_UPDATE_VALIDATION_RESPONSE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<BrigadeDto> doOnSave(BrigadeDto dto) {
        return Mono.just(dto)
                .flatMap(request -> {

                    final EpBrigadeEntity persistenceEntity = this.mapper.mapperToEntity(request);

                    final Optional<EpBrigadeStatusEntity> brigadeStatusEntity = this.statusRepository.findByUuId(request.getStatus());

                    if (brigadeStatusEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    final Optional<EpDivisionEntity> divisionEntity = this.divisionRepository.findByUuId(request.getDivision());

                    if (divisionEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    persistenceEntity.setUuId(UUID.randomUUID().toString());
                    persistenceEntity.setBrStatus(brigadeStatusEntity.get());
                    persistenceEntity.setBrDivision(divisionEntity.get());
                    persistenceEntity.setBrCreatedDate(Instant.now());

                    final EpBrigadeEntity entityResult = this.repository.save(persistenceEntity);
                    return Mono.just(this.mapper.mapperToDto(entityResult));
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_SAVE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<BrigadeDto> doOnUpdate(BrigadeDto dto) {
        return Mono.just(dto)
                .flatMap(request -> {

                    if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(request.getUuId()))) {
                        log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_INVALID_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_UUID));
                    }

                    final Optional<EpBrigadeEntity> persistenceEntity = this.repository.findByUuId(request.getUuId());
                    if (persistenceEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }


                    final Optional<EpBrigadeStatusEntity> brigadeStatusEntity = this.statusRepository.findByUuId(request.getStatus());

                    if (brigadeStatusEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_UPDATE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_UPDATE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    final Optional<EpDivisionEntity> divisionEntity = this.divisionRepository.findByUuId(request.getDivision());

                    if (divisionEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    final EpBrigadeEntity entityUpdate = persistenceEntity.get();
                    entityUpdate.setBrStatus(brigadeStatusEntity.get());
                    entityUpdate.setBrDivision(divisionEntity.get());
                    entityUpdate.setBrCode(request.getCode());
                    entityUpdate.setBrName(request.getName());
                    entityUpdate.setBrDescription(request.getDescription());
                    entityUpdate.setBrUpdatedDate(Instant.now());
                    final EpBrigadeEntity entityResult = this.repository.save(entityUpdate);
                    entityResult.setBrStatus(brigadeStatusEntity.get());
                    entityResult.setBrDivision(divisionEntity.get());

                    return Mono.just(this.mapper.mapperToDto(entityResult));
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    @Override
    public Mono<BrigadeDto> deleteByUuIdEntity(String uuId) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(uuId))) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_ID));
        }

        final Optional<EpBrigadeEntity> persistenceEntity = this.repository.findByUuId(uuId);

        if (persistenceEntity.isEmpty()) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_NOT_EXIST_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
        }

        this.repository.delete(persistenceEntity.get());
        return Mono.just(this.mapper.mapperToDto(persistenceEntity.get()))
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    private Mono<BrigadeDto> getBrigadeDto(Optional<EpBrigadeEntity> persistenceEntity, String successMessage, String messageExist) {
        return persistenceEntity.map(entity -> Mono.just(this.mapper.mapperToDto(entity))
                        .doOnSuccess(success -> log.debug(successMessage))
                        .doOnError(throwable -> log.error(throwable.getMessage()))
                        .log())
                .orElseGet(() -> Mono.error(() -> {
                    log.error(messageExist);
                    return new CommonException(messageExist, ResponseEnum.NOT_FOUNT_ENTITY);
                }));
    }


}