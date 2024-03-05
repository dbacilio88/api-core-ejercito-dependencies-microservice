package pe.mil.ejercito.microservice.repositories.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
/**
 * EpUnitEntity
 * <p>
 * EpUnitEntity class.
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
@Table(name = "EP_UNIT", indexes = {
        @Index(name = "EP_UNIT_UN1", columnList = "UN_UUID", unique = true),
        @Index(name = "EP_UNIT_UN2", columnList = "UN_CODE", unique = true),
        @Index(name = "EP_UNIT_UN3", columnList = "UN_NAME", unique = true)
})
public class EpUnitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EP_UNIT_SEQ")
    @SequenceGenerator(name = "EP_UNIT_SEQ", sequenceName = "EP_UNIT_SEQ", allocationSize = 1)
    @Column(name = "UN_ID", nullable = false)
    private Long id;

    @Size(max = 36)
    @NotNull
    @Column(name = "UN_UUID", nullable = false, length = 36)
    private String uuId;

    @ToString.Exclude
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private EpBrigadeEntity unBrigadeStatus;

    @Size(max = 6)
    @NotNull
    @Column(name = "UN_CODE", nullable = false, length = 6)
    private String code;

    @Size(max = 60)
    @NotNull
    @Column(name = "UN_NAME", nullable = false, length = 60)
    private String name;

    @Size(max = 120)
    @Column(name = "UN_DESCRIPTION", length = 120)
    private String description;

    @NotNull
    @Column(name = "UN_CREATED_DATE", nullable = false)
    private Instant unCreatedDate;

    @Column(name = "UN_UPDATED_DATE")
    private Instant unUpdatedDate;

}