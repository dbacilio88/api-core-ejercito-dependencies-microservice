package pe.mil.ejercito.microservice.repositories.entities;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * EpDivisionStatusEntity
 * <p>
 * EpDivisionStatusEntity class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 20/02/2024
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "EP_DIVISION_STATUS", indexes = {
        @Index(name = "EP_DIVISION_STATUS_UN1", columnList = "DS_UUID", unique = true),
        @Index(name = "EP_DIVISION_STATUS_UN2", columnList = "DS_CODE", unique = true),
        @Index(name = "EP_DIVISION_STATUS_UN3", columnList = "DS_NAME", unique = true)
})
public class EpDivisionStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EP_DIVISION_STATUS_SEQ")
    @SequenceGenerator(name = "EP_DIVISION_STATUS_SEQ", sequenceName = "EP_DIVISION_STATUS_SEQ", allocationSize = 1)
    @Column(name = "DS_ID", nullable = false)
    private Long id;

    @Size(max = 36)
    @NotNull
    @Column(name = "DS_UUID", nullable = false, length = 36)
    private String uuId;

    @Size(max = 11)
    @NotNull
    @Column(name = "DS_CODE", nullable = false, length = 9)
    private String code;

    @Size(max = 11)
    @NotNull
    @Column(name = "DS_NAME", nullable = false, length = 9)
    private String name;

    @Size(max = 20)
    @Column(name = "DS_DESCRIPTION", length = 20)
    private String description;
    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "diStatus")
    private Set<EpDivisionEntity> epDivisions = new LinkedHashSet<>();
}