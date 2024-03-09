package pe.mil.ejercito.microservice.repositories.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;
/**
 * EpUnitStatusEntity
 * <p>
 * EpUnitStatusEntity class.
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
@Table(name = "EP_UNIT_STATUS", indexes = {
        @Index(name = "EP_UNIT_STATUS_UN1", columnList = "US_UUID", unique = true),
        @Index(name = "EP_UNIT_STATUS_UN2", columnList = "US_CODE", unique = true),
        @Index(name = "EP_UNIT_STATUS_UN3", columnList = "US_NAME", unique = true),
})
public class EpUnitStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EP_UNID_STATUS_SEQ")
    @SequenceGenerator(name = "EP_UNID_STATUS_SEQ", sequenceName = "EP_UNID_STATUS_SEQ", allocationSize = 1)
    @Column(name = "US_ID", nullable = false)
    private Long id;

    @ToString.Include
    @Size(max = 36)
    @NotNull
    @Column(name = "US_UUID", nullable = false, length = 36)
    private String uuId;

    @ToString.Include
    @Size(max = 11)
    @NotNull
    @Column(name = "US_CODE", nullable = false, length = 11)
    private String usCode;

    @ToString.Include
    @Size(max = 11)
    @NotNull
    @Column(name = "US_NAME", nullable = false, length = 11)
    private String usName;

    @ToString.Include
    @Size(max = 20)
    @Column(name = "US_DESCRIPTION", length = 20)
    private String usDescription;

    @ToString.Exclude
    @OneToMany(mappedBy = "unStatus")
    private Set<EpUnitEntity> epUnits = new LinkedHashSet<>();

}