package pe.mil.ejercito.microservice.services.implementations;

import com.bxcode.tools.loader.componets.enums.ProcessResult;
import com.bxcode.tools.loader.componets.enums.ResponseEnum;
import com.bxcode.tools.loader.componets.exceptions.CommonException;
import com.bxcode.tools.loader.componets.helpers.CommonRequestHelper;
import com.bxcode.tools.loader.dto.PageableDto;
import com.bxcode.tools.loader.services.base.ReactorServiceBase;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.mil.ejercito.microservice.components.helper.PageableHelper;
import pe.mil.ejercito.microservice.components.mappers.IDivisionMapper;
import pe.mil.ejercito.microservice.components.validations.IDivisionValidation;
import pe.mil.ejercito.microservice.dtos.DivisionDto;
import pe.mil.ejercito.microservice.repositories.IEpDivisionRepository;
import pe.mil.ejercito.microservice.repositories.IEpDivisionStatusRepository;
import pe.mil.ejercito.microservice.repositories.entities.EpDivisionEntity;
import pe.mil.ejercito.microservice.repositories.entities.EpDivisionStatusEntity;
import pe.mil.ejercito.microservice.services.contracts.IDivisionDomainService;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bxcode.tools.loader.constants.BaseLoggerServicesConstant.*;

/**
 * DivisionDomainService
 * <p>
 * DivisionDomainService class.
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
public class DivisionDomainService extends ReactorServiceBase implements IDivisionDomainService {

    private final IEpDivisionRepository divisionRepository;
    private final IEpDivisionStatusRepository divisionStatusRepository;
    private final IDivisionMapper divisionMapper;

    public DivisionDomainService(final IEpDivisionRepository divisionRepository,
                                 final IEpDivisionStatusRepository divisionStatusRepository,
                                 final IDivisionMapper divisionMapper) {
        super("DivisionDomainService");
        this.divisionRepository = divisionRepository;
        this.divisionStatusRepository = divisionStatusRepository;
        this.divisionMapper = divisionMapper;
    }

    @Transactional
    @Override
    public Mono<DivisionDto> getByIdEntity(Long id) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidId(id))) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_ID));
        }

        final Optional<EpDivisionEntity> persistenceEntity = this.divisionRepository.findById(id);

        return getDivisionDto(
                persistenceEntity,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_FORMAT_SUCCESS,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_ID_NOT_EXIST_FORMAT_ERROR);
    }

    @Override
    public Mono<DivisionDto> getByUuIdEntity(String uuId) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(uuId))) {
            log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_UUID));
        }

        final Optional<EpDivisionEntity> persistenceEntity = this.divisionRepository.findDivisionByUuId(uuId);

        return getDivisionDto(
                persistenceEntity,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_FORMAT_SUCCESS,
                MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
    }


    @Override
    public Mono<DivisionDto> saveEntity(DivisionDto dto) {
        return doOnSave(dto)
                .flatMap(this::doOnValidateResponse)
                .doOnSuccess(success -> log.info(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @Override
    public Mono<DivisionDto> updateEntity(DivisionDto dto) {
        return doOnUpdate(dto)
                .flatMap(this::doOnValidateResponse)
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_UPDATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<DivisionDto> doOnValidateResponse(DivisionDto dto) {
        return IDivisionValidation.doOnValidationResponse().apply(dto)
                .flatMap(validation -> {
                    if (ProcessResult.PROCESS_FAILED.equals(validation.getProcessResult())) {
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_OR_UPDATE_VALIDATION_RESPONSE_FORMAT_ERROR, ResponseEnum.INTERNAL_SERVER_ERROR));
                    }
                    return Mono.just(dto);
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_OR_UPDATE_VALIDATION_RESPONSE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<DivisionDto> doOnSave(DivisionDto dto) {
        return Mono.just(dto)
                .flatMap(request -> {
                    final EpDivisionEntity persistenceEntity = this.divisionMapper.mapperToEntity(request);

                    final Optional<EpDivisionStatusEntity> divisionStatusEntity = this.divisionStatusRepository.findByUuId(request.getStatus());

                    if (divisionStatusEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_SAVE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    persistenceEntity.setUuId(UUID.randomUUID().toString());
                    persistenceEntity.setDiStatus(divisionStatusEntity.get());
                    persistenceEntity.setDiCreatedDate(Instant.now());

                    final EpDivisionEntity entityResult = this.divisionRepository.save(persistenceEntity);
                    return Mono.just(this.divisionMapper.mapperToDto(entityResult));
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_SAVE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    private Mono<DivisionDto> doOnUpdate(DivisionDto dto) {
        return Mono.just(dto)
                .flatMap(request -> {

                    if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(request.getUuId()))) {
                        log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_INVALID_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_UUID));
                    }

                    final Optional<EpDivisionEntity> persistenceEntity = this.divisionRepository.findDivisionByUuId(request.getUuId());

                    if (persistenceEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }


                    final Optional<EpDivisionStatusEntity> divisionStatusEntity = this.divisionStatusRepository.findByUuId(request.getStatus());

                    if (divisionStatusEntity.isEmpty()) {
                        log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_UPDATE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR);
                        return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_UPDATE_FIND_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
                    }

                    final EpDivisionEntity entityUpdate = persistenceEntity.get();
                    entityUpdate.setDiStatus(divisionStatusEntity.get());
                    entityUpdate.setCode(request.getCode());
                    entityUpdate.setName(request.getName());
                    entityUpdate.setDescription(request.getDescription());
                    entityUpdate.setDiUpdatedDate(Instant.now());
                    final EpDivisionEntity entityResult = this.divisionRepository.save(entityUpdate);
                    entityResult.setDiStatus(divisionStatusEntity.get());
                    return Mono.just(this.divisionMapper.mapperToDto(entityResult));
                }).doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_ON_UPDATE_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    @Override
    public Mono<DivisionDto> deleteByUuIdEntity(String uuId) {
        if (Boolean.TRUE.equals(CommonRequestHelper.isInvalidUuId(uuId))) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_INVALID_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_INVALID_FORMAT_ERROR, ResponseEnum.ERROR_INVALID_DATA_ID));
        }

        final Optional<EpDivisionEntity> persistenceEntity = this.divisionRepository.findDivisionByUuId(uuId);

        if (persistenceEntity.isEmpty()) {
            log.error(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_NOT_EXIST_FORMAT_ERROR);
            return Mono.error(() -> new CommonException(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_NOT_EXIST_FORMAT_ERROR, ResponseEnum.NOT_FOUNT_ENTITY));
        }

        this.divisionRepository.delete(persistenceEntity.get());
        return Mono.just(this.divisionMapper.mapperToDto(persistenceEntity.get()))
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_DELETE_BY_UUID_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


    private Mono<DivisionDto> getDivisionDto(Optional<EpDivisionEntity> dEntity, String successMessage, String messageExist) {
        return dEntity.map(division -> Mono.just(this.divisionMapper.mapperToDto(division))
                        .doOnSuccess(success -> log.debug(successMessage))
                        .doOnError(throwable -> log.error(throwable.getMessage())))
                .orElseGet(() -> Mono.error(() -> {
                    log.error(messageExist);
                    return new CommonException(messageExist, ResponseEnum.NOT_FOUNT_ENTITY);
                }));
    }


    @Override
    public Mono<List<DivisionDto>> getAllEntities(Long statusId, String limit, String page, PageableDto pageable) {
        Pageable paging = PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(limit));
        Page<EpDivisionEntity> entityPage = this.divisionRepository.findAll(statusId, paging);
        List<DivisionDto> brigades = this.divisionMapper.mapperToList(entityPage.getContent());
        PageableHelper.generatePaginationDetails(entityPage, page, limit, pageable);
        return Mono.just(brigades)
                .doOnSuccess(success -> log.debug(MICROSERVICE_SERVICE_DOMAIN_ENTITY_FIND_ALL_FORMAT_SUCCESS))
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }
}