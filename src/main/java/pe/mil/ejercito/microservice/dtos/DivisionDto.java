package pe.mil.ejercito.microservice.dtos;

import com.bxcode.tools.loader.componets.annotations.Uuid;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * DivisionDto
 * <p>
 * DivisionDto class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 2/03/2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DivisionDto implements Serializable {

    private static final long serialVersionUID = 6513459404533226833L;

    Long id;

    String uuId;

    @Size(max = 36, min = 36)
    @NotNull
    @NotBlank
    @Uuid
    String status;

    @Size(max = 2, min = 2)
    @NotNull
    @NotBlank
    String code;

    @Size(max = 60)
    @NotNull
    @NotBlank
    String name;

    @Size(max = 120)
    @NotNull
    @NotBlank
    String description;

    private Instant updated;
    private Instant created;
}