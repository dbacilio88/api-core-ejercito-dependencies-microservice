package pe.mil.ejercito.microservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.mil.ejercito.microservice.repositories.entities.EpBrigadeEntity;

import java.util.Optional;

/**
 * IEpBrigadeRepository
 * <p>
 * IEpBrigadeRepository interface.
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
public interface IEpBrigadeRepository extends JpaRepository<EpBrigadeEntity, Long>, JpaSpecificationExecutor<EpBrigadeEntity> {
    @Query(value = "SELECT b FROM EpBrigadeEntity b LEFT JOIN FETCH b.brStatus LEFT JOIN FETCH b.brDivision WHERE b.uuId = :uuId")
    Optional<EpBrigadeEntity> findBrigadeByUuId(String uuId);

    @Query(value = "SELECT b FROM EpBrigadeEntity b " +
            "INNER JOIN FETCH b.brDivision d " +
            "INNER JOIN FETCH b.brStatus s " +
            "WHERE (:divisionId is null or d.id = :divisionId) " +
            "AND (:statusId is null or s.id = :statusId)",
            countQuery = "SELECT COUNT(b) FROM EpBrigadeEntity b " +
                    "LEFT JOIN b.brDivision d " +
                    "LEFT JOIN b.brStatus s " +
                    "WHERE (:divisionId is null or d.id = :divisionId) " +
                    "AND (:statusId is null or s.id = :statusId)")
    Page<EpBrigadeEntity> findAll(@Param("divisionId") Long divisionId, @Param("statusId") Long statusId, Pageable pageable);
}