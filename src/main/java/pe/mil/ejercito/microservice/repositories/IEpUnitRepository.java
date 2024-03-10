package pe.mil.ejercito.microservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.mil.ejercito.microservice.repositories.entities.EpUnitEntity;

import java.util.Optional;

/**
 * IEpUnitRepository
 * <p>
 * IEpUnitRepository interface.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 21/02/2024
 */
@Repository
public interface IEpUnitRepository extends JpaRepository<EpUnitEntity, Long> {
    @Query(value = "SELECT u FROM EpUnitEntity u LEFT JOIN FETCH u.unStatus LEFT JOIN FETCH u.unBrigade  WHERE u.uuId = :uuId")
    Optional<EpUnitEntity> findUnitByUuId(String uuId);

    @Query(value = "SELECT u FROM EpUnitEntity u " +
            "INNER JOIN FETCH u.unBrigade b " +
            "INNER JOIN FETCH u.unStatus s " +
            "WHERE (:brigadeId is null or b.id = :brigadeId) " +
            "AND (:statusId is null or s.id = :statusId)",
            countQuery = "SELECT COUNT(u) FROM EpUnitEntity u " +
                    "LEFT JOIN u.unBrigade b " +
                    "LEFT JOIN u.unStatus s " +
                    "WHERE (:brigadeId is null or b.id = :brigadeId) " +
                    "AND (:statusId is null or s.id = :statusId)")
    Page<EpUnitEntity> findAll(@Param("brigadeId") Long divisionId, @Param("statusId") Long statusId, Pageable pageable);

}