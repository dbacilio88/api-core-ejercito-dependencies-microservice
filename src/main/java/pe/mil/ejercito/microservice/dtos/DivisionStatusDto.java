package pe.mil.ejercito.microservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DivisionStatusDto implements Serializable {

    private static final long serialVersionUID = 6513459404533226833L;

    Long id;

    @Size(max = 36, min = 36)
    @NotNull
    @NotBlank
    String uuId;

    @Size(max = 11)
    @NotNull
    @NotBlank
    String code;

    @Size(max = 11)
    @NotNull
    @NotBlank
    String name;

    @Size(max = 20)
    @NotNull
    @NotBlank
    String description;
}