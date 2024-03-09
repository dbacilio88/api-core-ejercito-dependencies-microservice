package pe.mil.ejercito.microservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.mil.ejercito.microservice.repositories.entities.EpUnitStatusEntity;

import java.util.Optional;

/**
 * IEpUnitStatusRepository
 * <p>
 * IEpUnitStatusRepository interface.
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
public interface IEpUnitStatusRepository extends JpaRepository<EpUnitStatusEntity, Long> {

    Optional<EpUnitStatusEntity> findByUuId(String uuId);
}