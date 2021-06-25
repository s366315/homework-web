package org.hillel.web.persistence.jpa.repository;

import org.hillel.web.persistence.entity.JourneyEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JourneyJpaRepository extends CommonJpaRepository<JourneyEntity, Long>,
        JpaSpecificationExecutor<JourneyEntity> {
}
