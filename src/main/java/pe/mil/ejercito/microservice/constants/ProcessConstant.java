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


    public static final String FIND_ALL_BRIGADE_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/brigade-status";
    public static final String FIND_BY_ID_BRIGADE_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/brigade-status/id/{statusId}/status";
    public static final String FIND_BY_UUID_BRIGADE_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/brigade-status/uuid/{uuId}/status";
    public static final String CREATE_BRIGADE_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/brigade-status/create";
    public static final String UPDATE_BRIGADE_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/brigade-status/update";
    public static final String DELETE_BRIGADE_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/brigade-status/delete/uuId/{uuId}/status";


    public static final String FIND_ALL_UNIT_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/unit-status";
    public static final String FIND_BY_ID_UNIT_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/unit-status/id/{statusId}/status";
    public static final String FIND_BY_UUID_UNIT_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/unit-status/uuid/{uuId}/status";
    public static final String CREATE_UNIT_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/unit-status/create";
    public static final String UPDATE_UNIT_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/unit-status/update";
    public static final String DELETE_UNIT_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/unit-status/delete/uuId/{uuId}/status";


    public static final String FIND_ALL_DIVISION_PATH = MICROSERVICE_PATH_CONTEXT + "/divisions";
    public static final String FIND_BY_ID_DIVISION_PATH = MICROSERVICE_PATH_CONTEXT + "/divisions/id/{divisionId}/division";
    public static final String FIND_BY_UUID_DIVISION_PATH = MICROSERVICE_PATH_CONTEXT + "/divisions/uuid/{uuId}/division";
    public static final String CREATE_DIVISION_PATH = MICROSERVICE_PATH_CONTEXT + "/divisions/create";
    public static final String UPDATE_DIVISION_PATH = MICROSERVICE_PATH_CONTEXT + "/divisions/update";
    public static final String DELETE_DIVISION_PATH = MICROSERVICE_PATH_CONTEXT + "/divisions/delete/uuId/{uuId}/division";


    public static final String FIND_ALL_BRIGADE_PATH = MICROSERVICE_PATH_CONTEXT + "/brigades";
    public static final String FIND_BY_ID_BRIGADE_PATH = MICROSERVICE_PATH_CONTEXT + "/brigades/id/{brigadeId}/brigade";
    public static final String FIND_BY_UUID_BRIGADE_PATH = MICROSERVICE_PATH_CONTEXT + "/brigades/uuid/{uuId}/brigade";
    public static final String CREATE_BRIGADE_PATH = MICROSERVICE_PATH_CONTEXT + "/brigades/create";
    public static final String UPDATE_BRIGADE_PATH = MICROSERVICE_PATH_CONTEXT + "/brigades/update";
    public static final String DELETE_BRIGADE_PATH = MICROSERVICE_PATH_CONTEXT + "/brigades/delete/uuId/{uuId}/brigade";


    public static final String FIND_ALL_UNIT_PATH = MICROSERVICE_PATH_CONTEXT + "/units";
    public static final String FIND_BY_ID_UNIT_PATH = MICROSERVICE_PATH_CONTEXT + "/units/id/{unitId}/unit";
    public static final String FIND_BY_UUID_UNIT_PATH = MICROSERVICE_PATH_CONTEXT + "/units/uuid/{uuId}/unit";
    public static final String CREATE_UNIT_PATH = MICROSERVICE_PATH_CONTEXT + "/units/create";
    public static final String UPDATE_UNIT_PATH = MICROSERVICE_PATH_CONTEXT + "/units/update";
    public static final String DELETE_UNIT_PATH = MICROSERVICE_PATH_CONTEXT + "/units/delete/uuId/{uuId}/unit";
}
