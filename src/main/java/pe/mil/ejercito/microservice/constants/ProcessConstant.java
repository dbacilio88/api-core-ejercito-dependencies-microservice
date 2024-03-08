package pe.mil.ejercito.microservice.constants;

import lombok.experimental.UtilityClass;

/**
 * ProcessConstant
 * <p>
 * ProcessConstant class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 2/03/2024
 */
@UtilityClass
public class ProcessConstant {
    public static final String MICROSERVICE_PATH_CONTEXT = "";
    public static final String POD_INFO_PATH = MICROSERVICE_PATH_CONTEXT + "/podInfo";
    public static final String FIND_ALL_DIVISION_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/division-status";
    public static final String FIND_BY_ID_DIVISION_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/division-status/id/{statusId}/status";
    public static final String FIND_BY_UUID_DIVISION_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/division-status/uuid/{uuId}/status";
    public static final String CREATE_DIVISION_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/division-status/create";
    public static final String UPDATE_DIVISION_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/division-status/update";
    public static final String DELETE_DIVISION_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/division-status/delete/uuId/{uuId}/status";
}