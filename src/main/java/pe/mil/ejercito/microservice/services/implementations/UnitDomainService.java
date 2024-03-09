package pe.mil.ejercito.microservice.services.implementations;

import com.bxcode.tools.loader.componets.enums.ProcessResult;
import com.bxcode.tools.loader.componets.enums.ResponseEnum;
import com.bxcode.tools.loader.componets.exceptions.CommonException;
import com.bxcode.tools.loader.componets.helpers.CommonRequestHelper;
import com.bxcode.tools.loader.services.base.ReactorServiceBase;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.mil.ejercito.microservice.components.mappers.IUnitMapper;
import pe.mil.ejercito.microservice.components.validations.IUnitValidation;
import pe.mil.ejercito.microservice.dtos.UnitDto;
import pe.mil.ejercito.microservice.repositories.IEpBrigadeRepository;
import pe.mil.ejercito.microservice.repositories.IEpUnitRepository;
import pe.mil.ejercito.microservice.repositories.IEpUnitStatusRepository;
import pe.mil.ejercito.microservice.repositories.entities.EpBrigadeEntity;
import pe.mil.ejercito.microservice.repositories.entities.EpUnitEntity;
import pe.mil.ejercito.microservice.repositories.entities.EpUnitStatusEntity;
import pe.mil.ejercito.microservice.services.contracts.IUnitDomainService;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bxcode.tools.loader.constants.BaseLoggerServicesConstant.*;

/**
 * UnitDomainService
 * <p>
 * UnitDomainService class.
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
public class UnitDomainService extends ReactorServiceBase implements IUnitDomainService {

    private final IEpUnitRepository repository;
    private final IEpUnitStatusRepository statusRepository;
    private final IEpBrigadeRepository brigadeRepository;
    private final IUnitMapper mapper;

    public UnitDomainService(final IEpUnitRepository repository,
                             final IEpUnitStatusRepository statusRepository,
                             final IEpBrigadeRepository brigadeRepository,
                             final IUnitMapper mapper) {
        super("UnitDomainService");
        this.repository = repository;
        this.statusRepository = statusRepository;
        this.brigadeRepository = brigadeRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<List<UnitDto>> getAllEntities() {
        final Iterable<EpUnitEntity> persistenceEntities = this.repository.findAllCustom();
        final List<UnitDto> list = this.mapper.mapperToList(persistenceEntities);
        return Mono.just(list)
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_ALL_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Transactional
    @Override
    public Mono<UnitDto> getByIdEntity(Long id) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidId(id))) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_ID));
        }

        final Optional<EpUnitEntity> persistenceEntity = this.repository.findById(id);

        return getUnitDto(
                persistenceEntity,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_FORMAT_SUCCESS,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_NOT_EXIST_FORMAT_ERROR);
    }

    @Override
    public Mono<UnitDto> getByUuIdEntity(String uuId) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(uuId))) {
            log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_UUID));
        }

        final Optional<EpUnitEntity> persistenceEntity = this.repository.findByUuId(uuId);

        return getUnitDto(
                persistenceEntity,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_FORMAT_SUCCESS,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
    }


    @Override
    public Mono<UnitDto> saveEntity(UnitDto dto) {
        return doOnSave(dto)
                .flatMap(this::doOnValidateResponse)
                .doOnSuccess(success -> log.info(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Override
    public Mono<UnitDto> updateEntity(UnitDto dto) {
        return doOnUpdate(dto)
                .flatMap(this::doOnValidateResponse)
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_UPDATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<UnitDto> doOnValidateResponse(UnitDto dto) {
        return IUnitValidation.doOnValidationResponse().apply(dto)
                .flatMap(validation -> {
                    if (ProcessResult.PROCESS_FAILED.equals(validation.getProcessResult())) {
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_OR_UPDATE_VALIDATION_RESPONSE_FORMAT_ERROR, ResponseEnum.INTERNAL_SERVER_ERROR));
                    }
                    return Mono.just(dto);
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_OR_UPDATE_VALIDATION_RESPONSE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<UnitDto> doOnSave(UnitDto dto) {
        return Mono.just(dto)
                .flatMap(request -> {

                    final EpUnitEntity persistenceEntity = this.mapper.mapperToEntity(request);

                    final Optional<EpUnitStatusEntity> unitStatusEntity = this.statusRepository.findByUuId(request.getStatus());

                    if (unitStatusEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    final Optional<EpBrigadeEntity> brigadeEntity = this.brigadeRepository.findByUuId(request.getBrigade());

                    if (brigadeEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    persistenceEntity.setUuId(UUID.randomUUID().toString());
                    persistenceEntity.setUnStatus(unitStatusEntity.get());
                    persistenceEntity.setUnBrigade(brigadeEntity.get());
                    persistenceEntity.setUnCreatedDate(Instant.now());

                    final EpUnitEntity entityResult = this.repository.save(persistenceEntity);
                    return Mono.just(this.mapper.mapperToDto(entityResult));
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_SAVE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<UnitDto> doOnUpdate(UnitDto dto) {
        return Mono.just(dto)
                .flatMap(request -> {

                    if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(request.getUuId()))) {
                        log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_INVALID_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_UUID));
                    }

                    final Optional<EpUnitEntity> persistenceEntity = this.repository.findByUuId(request.getUuId());
                    if (persistenceEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }


                    final Optional<EpUnitStatusEntity> unitStatusEntity = this.statusRepository.findByUuId(request.getStatus());

                    if (unitStatusEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_UPDATE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_UPDATE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }


                    final Optional<EpBrigadeEntity> brigadeEntity = this.brigadeRepository.findByUuId(request.getBrigade());

                    if (brigadeEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    final EpUnitEntity entityUpdate = persistenceEntity.get();
                    entityUpdate.setUnStatus(unitStatusEntity.get());
                    entityUpdate.setUnBrigade(brigadeEntity.get());
                    entityUpdate.setUnCode(request.getCode());
                    entityUpdate.setUnName(request.getName());
                    entityUpdate.setUnDescription(request.getDescription());
                    entityUpdate.setUnUpdatedDate(Instant.now());
                    final EpUnitEntity entityResult = this.repository.save(entityUpdate);
                    entityResult.setUnStatus(unitStatusEntity.get());
                    entityResult.setUnBrigade(brigadeEntity.get());
                    return Mono.just(this.mapper.mapperToDto(entityResult));
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    @Override
    public Mono<UnitDto> deleteByUuIdEntity(String uuId) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(uuId))) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_ID));
        }

        final Optional<EpUnitEntity> persistenceEntity = this.repository.findByUuId(uuId);

        if (persistenceEntity.isEmpty()) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_NOT_EXIST_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
        }

        this.repository.delete(persistenceEntity.get());
        return Mono.just(this.mapper.mapperToDto(persistenceEntity.get()))
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    private Mono<UnitDto> getUnitDto(Optional<EpUnitEntity> persistenceEntity, String successMessage, String messageExist) {
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