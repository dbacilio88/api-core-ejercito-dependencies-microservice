package pe.mil.ejercito.microservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.mil.ejercito.microservice.repositories.entities.EpUnitEntity;

import java.util.List;
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
    Optional<EpUnitEntity> findByUuId(String uuId);

    @Query(value = "SELECT u FROM EpUnitEntity u LEFT JOIN FETCH u.unStatus LEFT JOIN FETCH u.unBrigade")
    List<EpUnitEntity> findAllCustom();
}