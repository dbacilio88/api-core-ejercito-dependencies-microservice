package pe.mil.ejercito.microservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.mil.ejercito.microservice.repositories.entities.EpBrigadeEntity;

import java.util.List;
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
public interface IEpBrigadeRepository extends JpaRepository<EpBrigadeEntity, Long> {
    @Query(value = "SELECT b FROM EpBrigadeEntity b LEFT JOIN FETCH b.brStatus LEFT JOIN FETCH b.brDivision WHERE b.uuId = :uuId")
    Optional<EpBrigadeEntity> findBrigadeByUuId(String uuId);

    @Query(value = "SELECT b FROM EpBrigadeEntity b LEFT JOIN FETCH b.brStatus  LEFT JOIN FETCH b.brDivision")
    List<EpBrigadeEntity> findAllCustom();
}