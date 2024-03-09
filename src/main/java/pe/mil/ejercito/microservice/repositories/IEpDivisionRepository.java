package pe.mil.ejercito.microservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.mil.ejercito.microservice.repositories.entities.EpDivisionEntity;

import java.util.List;
import java.util.Optional;

/**
 * IEpDivisionRepository
 * <p>
 * IEpDivisionRepository interface.
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
public interface IEpDivisionRepository extends JpaRepository<EpDivisionEntity, Long> {
    @Query(value = "SELECT d FROM EpDivisionEntity d LEFT JOIN FETCH d.diStatus WHERE d.uuId = :uuId")
    Optional<EpDivisionEntity> findByUuId(String uuId);

    @Query(value = "SELECT d FROM EpDivisionEntity d LEFT JOIN FETCH d.diStatus")
    List<EpDivisionEntity> findAllCustom();
}