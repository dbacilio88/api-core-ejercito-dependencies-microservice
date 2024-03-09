package pe.mil.ejercito.microservice.repositories.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * EpBrigadeStatusEntity
 * <p>
 * EpBrigadeStatusEntity class.
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
@Table(name = "EP_BRIGADE_STATUS", indexes = {
        @Index(name = "EP_BRIGADE_STATUS_UN1", columnList = "BS_UUID", unique = true),
        @Index(name = "EP_BRIGADE_STATUS_UN2", columnList = "BS_CODE", unique = true),
        @Index(name = "EP_BRIGADE_STATUS_UN3", columnList = "BS_NAME", unique = true)
})
public class EpBrigadeStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EP_BRIGADE_STATUS_SEQ")
    @SequenceGenerator(name = "EP_BRIGADE_STATUS_SEQ", sequenceName = "EP_BRIGADE_STATUS_SEQ", allocationSize = 1)
    @Column(name = "BS_ID", nullable = false)
    private Long id;

    @Size(max = 36)
    @NotNull
    @Column(name = "BS_UUID", nullable = false, length = 36)
    private String uuId;

    @Size(max = 11)
    @NotNull
    @Column(name = "BS_CODE", nullable = false, length = 11)
    private String bsCode;

    @Size(max = 11)
    @NotNull
    @Column(name = "BS_NAME", nullable = false, length = 11)
    private String bsName;

    @Size(max = 20)
    @Column(name = "BS_DESCRIPTION", length = 20)
    private String bsDescription;

    @OneToMany(mappedBy = "brStatus")
    @ToString.Exclude
    private Set<EpBrigadeEntity> epBrigades = new LinkedHashSet<>();

}