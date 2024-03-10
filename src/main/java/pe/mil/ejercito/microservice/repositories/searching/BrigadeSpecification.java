package pe.mil.ejercito.microservice.repositories.searching;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;
import pe.mil.ejercito.microservice.repositories.entities.EpBrigadeEntity;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

/**
 * BrigadeSpecification
 * <p>
 * BrigadeSpecification class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 9/03/2024
 */

@UtilityClass
@Log4j2
public class BrigadeSpecification {

    public static Specification<EpBrigadeEntity> brigadeEntitySpecification(Long divisionId, Long statusId) {

        Specification<EpBrigadeEntity> specification;
        Specification<EpBrigadeEntity> temporal;
        specification = findAllByDivisionIdAndStatusId(divisionId, statusId);
        log.debug("specification {} ", specification.toString());
        temporal = Specification.where(specification);
        return temporal;
    }


    private static Specification<EpBrigadeEntity> findAllByDivisionIdAndStatusId(Long divisionId, Long statusId) {
        return ((root, query, criteriaBuilder) -> {
            root.fetch("brDivision", JoinType.INNER);
            root.fetch("brStatus", JoinType.INNER);
            // Creating predicates for filtering
            Predicate divisionPredicate = criteriaBuilder.equal(root.get("brDivision").get("id"), divisionId);
            Predicate statusPredicate = criteriaBuilder.equal(root.get("brStatus").get("id"), statusId);
            // Combining predicates using AND
            Predicate finalPredicate = criteriaBuilder.and(divisionPredicate, statusPredicate);
            // Setting up the query
            query.distinct(true);
            return finalPredicate;
        });
    }
}


