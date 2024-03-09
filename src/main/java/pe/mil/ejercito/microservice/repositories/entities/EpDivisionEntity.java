package pe.mil.ejercito.microservice.repositories.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
/**
 * EpDivisionEntity
 * <p>
 * EpDivisionEntity class.
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
@Table(name = "EP_DIVISION", indexes = {
        @Index(name = "EP_DIVISION_UN1", columnList = "DI_UUID", unique = true),
        @Index(name = "EP_DIVISION_UN2", columnList = "DI_CODE", unique = true),
        @Index(name = "EP_DIVISION_UN3", columnList = "DI_NAME", unique = true)
})
public class EpDivisionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EP_DIVISION_SEQ")
    @SequenceGenerator(name = "EP_DIVISION_SEQ", sequenceName = "EP_DIVISION_SEQ", allocationSize = 1)
    @Column(name = "DI_ID", nullable = false)
    private Long id;

    @Size(max = 36)
    @NotNull
    @Column(name = "DI_UUID", nullable = false, length = 36)
    private String uuId;

    @NotNull
    @ToString.Exclude
    @JoinColumn(name = "DI_STATUS", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private EpDivisionStatusEntity diStatus;

    @Size(max = 2)
    @NotNull
    @Column(name = "DI_CODE", nullable = false, length = 2)
    private String code;

    @Size(max = 60)
    @NotNull
    @Column(name = "DI_NAME", nullable = false, length = 60)
    private String name;

    @Size(max = 120)
    @Column(name = "DI_DESCRIPTION", length = 120)
    private String description;

    @NotNull
    @Column(name = "DI_CREATED_DATE", nullable = false)
    private Instant diCreatedDate;

    @Column(name = "DI_UPDATED_DATE")
    private Instant diUpdatedDate;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "brDivision")
    private Set<EpBrigadeEntity> epBrigades = new LinkedHashSet<>();

}