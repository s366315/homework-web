package org.hillel.web.persistence.jpa.repository;

import org.hillel.web.persistence.entity.SeatInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SeatInfoJpaRepository extends JpaRepository<SeatInfoEntity, Long>,
        JpaSpecificationExecutor<SeatInfoEntity> {
}
