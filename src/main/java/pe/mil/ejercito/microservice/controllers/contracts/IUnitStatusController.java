package pe.mil.ejercito.microservice.controllers.contracts;

import com.bxcode.tools.loader.constants.CategoryRestConstant;
import com.bxcode.tools.loader.dto.base.ResponseBase;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import pe.mil.ejercito.microservice.dtos.UnitStatusDto;
import reactor.core.publisher.Mono;

/**
 * IUnitStatusController
 * <p>
 * IUnitStatusController interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 26/02/2024
 */
public interface IUnitStatusController {

    @Operation(summary = "Returns the status division iterable", description = CategoryRestConstant.CATEGORY_GENERAL_DESCRIPTION, tags = {CategoryRestConstant.CATEGORY_GENERAL})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = UnitStatusDto.class))),
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = ResponseBase.class), examples = {
                            @ExampleObject(description = "This successful response"),
                            @ExampleObject(description = "This error response")
                    })),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = HttpHeaders.ACCEPT, value = MediaType.APPLICATION_JSON_VALUE, required = true, paramType = "header", dataTypeClass = String.class, defaultValue = MediaType.APPLICATION_JSON_VALUE),
            @ApiImplicitParam(name = HttpHeaders.CONTENT_TYPE, value = MediaType.APPLICATION_JSON_VALUE, required = true, paramType = "header", dataTypeClass = String.class, defaultValue = MediaType.APPLICATION_JSON_VALUE)
    })
    Mono<ResponseEntity<Object>> doOnFindAllExecute();

    @Operation(summary = "Returns the status division by id", description = CategoryRestConstant.CATEGORY_GENERAL_DESCRIPTION, tags = {CategoryRestConstant.CATEGORY_GENERAL})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = UnitStatusDto.class))),
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = ResponseBase.class), examples = {
                            @ExampleObject(description = "This successful response"),
                            @ExampleObject(description = "This error response")
                    })),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = HttpHeaders.ACCEPT, value = MediaType.APPLICATION_JSON_VALUE, required = true, paramType = "header", dataTypeClass = String.class, defaultValue = MediaType.APPLICATION_JSON_VALUE),
            @ApiImplicitParam(name = HttpHeaders.CONTENT_TYPE, value = MediaType.APPLICATION_JSON_VALUE, required = true, paramType = "header", dataTypeClass = String.class, defaultValue = MediaType.APPLICATION_JSON_VALUE)
    })
    Mono<ResponseEntity<Object>> doOnFindByIdExecute(Long id);

    @Operation(summary = "Returns the status division by uuId", description = CategoryRestConstant.CATEGORY_GENERAL_DESCRIPTION, tags = {CategoryRestConstant.CATEGORY_GENERAL})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = UnitStatusDto.class))),
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = ResponseBase.class), examples = {
                            @ExampleObject(description = "This successful response"),
                            @ExampleObject(description = "This error response")
                    })),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = HttpHeaders.ACCEPT, value = MediaType.APPLICATION_JSON_VALUE, required = true, paramType = "header", dataTypeClass = String.class, defaultValue = MediaType.APPLICATION_JSON_VALUE),
            @ApiImplicitParam(name = HttpHeaders.CONTENT_TYPE, value = MediaType.APPLICATION_JSON_VALUE, required = true, paramType = "header", dataTypeClass = String.class, defaultValue = MediaType.APPLICATION_JSON_VALUE)
    })
    Mono<ResponseEntity<Object>> doOnFindByUuIdExecute(String uuId);

    @Operation(summary = "Returns the status division created", description = CategoryRestConstant.CATEGORY_GENERAL_DESCRIPTION, tags = {CategoryRestConstant.CATEGORY_GENERAL})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = UnitStatusDto.class))),
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = ResponseBase.class), examples = {
                            @ExampleObject(description = "This successful response"),
                            @ExampleObject(description = "This error response")
                    })),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = HttpHeaders.ACCEPT, value = MediaType.APPLICATION_JSON_VALUE, required = true, paramType = "header", dataTypeClass = String.class, defaultValue = MediaType.APPLICATION_JSON_VALUE),
            @ApiImplicitParam(name = HttpHeaders.CONTENT_TYPE, value = MediaType.APPLICATION_JSON_VALUE, required = true, paramType = "header", dataTypeClass = String.class, defaultValue = MediaType.APPLICATION_JSON_VALUE)
    })
    Mono<ResponseEntity<Object>> doOnCreateExecute(UnitStatusDto dto);

    @Operation(summary = "Returns the status division updated", description = CategoryRestConstant.CATEGORY_GENERAL_DESCRIPTION, tags = {CategoryRestConstant.CATEGORY_GENERAL})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = UnitStatusDto.class))),
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = ResponseBase.class), examples = {
                            @ExampleObject(description = "This successful response"),
                            @ExampleObject(description = "This error response")
                    })),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = HttpHeaders.ACCEPT, value = MediaType.APPLICATION_JSON_VALUE, required = true, paramType = "header", dataTypeClass = String.class, defaultValue = MediaType.APPLICATION_JSON_VALUE),
            @ApiImplicitParam(name = HttpHeaders.CONTENT_TYPE, value = MediaType.APPLICATION_JSON_VALUE, required = true, paramType = "header", dataTypeClass = String.class, defaultValue = MediaType.APPLICATION_JSON_VALUE)
    })
    Mono<ResponseEntity<Object>> doOnUpdateExecute(UnitStatusDto dto);

    @Operation(summary = "Returns true to deleted", description = CategoryRestConstant.CATEGORY_GENERAL_DESCRIPTION, tags = {CategoryRestConstant.CATEGORY_GENERAL})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = UnitStatusDto.class))),
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = ResponseBase.class), examples = {
                            @ExampleObject(description = "This successful response"),
                            @ExampleObject(description = "This error response")
                    })),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = HttpHeaders.ACCEPT, value = MediaType.APPLICATION_JSON_VALUE, required = true, paramType = "header", dataTypeClass = String.class, defaultValue = MediaType.APPLICATION_JSON_VALUE),
            @ApiImplicitParam(name = HttpHeaders.CONTENT_TYPE, value = MediaType.APPLICATION_JSON_VALUE, required = true, paramType = "header", dataTypeClass = String.class, defaultValue = MediaType.APPLICATION_JSON_VALUE)
    })
    Mono<ResponseEntity<Object>> doOnDeleteExecute(String uuId);
}