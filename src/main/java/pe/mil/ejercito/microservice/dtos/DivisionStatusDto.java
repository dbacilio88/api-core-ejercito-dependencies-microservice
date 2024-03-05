package pe.mil.ejercito.microservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

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
    String uuId;
    String code;
    String name;
    String description;
}