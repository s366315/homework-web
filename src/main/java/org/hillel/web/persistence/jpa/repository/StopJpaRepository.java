package org.hillel.web.persistence.jpa.repository;

import org.hillel.web.persistence.entity.StopEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StopJpaRepository extends CommonJpaRepository<StopEntity, Long>,
        JpaSpecificationExecutor<StopEntity> {
}
