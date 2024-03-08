package pe.mil.ejercito.microservice.constants;

import lombok.experimental.UtilityClass;

/**
 * LoggerConstant
 * <p>
 * LoggerConstant class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 20/02/2024
 */

@UtilityClass
public class LoggerConstant {
    public static final String HTTP_RESPONSE_FORMAT = "Response type [{}] from URL [{}] with state code [{}]";
    public static final String HTTP_WRITE_AND_FLUSH_WITH_FORMAT = "Response with state code[{}]";
    public static final String MICROSERVICE_CONTROLLER_DOMAIN_ENTITY_FIND_ALL_FORMAT_SUCCESS = "success in process doOnFindAllExecute";
    public static final String MICROSERVICE_CONTROLLER_DOMAIN_ENTITY_FIND_BY_ID_FORMAT_SUCCESS = "success in process doOnFindByIdExecute";
    public static final String MICROSERVICE_CONTROLLER_DOMAIN_ENTITY_FIND_BY_UUID_FORMAT_SUCCESS = "success in process doOnFindByUuIdExecute";
    public static final String MICROSERVICE_CONTROLLER_DOMAIN_ENTITY_CREATE_FORMAT_SUCCESS = "success in process doOnCreateExecute";
    public static final String MICROSERVICE_CONTROLLER_DOMAIN_ENTITY_UPDATE_FORMAT_SUCCESS = "success in process doOnUpdateExecute";
    public static final String MICROSERVICE_CONTROLLER_DOMAIN_ENTITY_DELETE_FORMAT_SUCCESS = "success in process doOnDeleteExecute";
    public static final String MICROSERVICE_PROCESS_VALIDATION_PARAMETER_REQUIRED = "error the fields is required";


}