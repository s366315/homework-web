package org.hillel.web.service;

import org.hillel.web.persistence.entity.StopEntity;
import org.hillel.web.persistence.jpa.repository.StopJpaRepository;
import org.hillel.web.persistence.jpa.repository.specification.StopSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class TransactionalStopService extends AbstractTransactionalService<StopEntity> {

    private final StopJpaRepository stopRepository;

    @Autowired
    public TransactionalStopService(StopJpaRepository stopRepository) {
        super(stopRepository);
        this.stopRepository = stopRepository;
    }

    @Transactional
    public StopEntity createOrUpdate(StopEntity stopEntity){
        return stopRepository.save(stopEntity);
    }

    @Transactional
    public StopEntity findById(Long id, boolean withDep){
        final Optional<StopEntity> stopEntity = stopRepository.findById(id);
        if (stopEntity.isEmpty()) return null;
        if (!withDep) return stopEntity.get();
        stopEntity.get().getStopAddInfo();
        return stopEntity.get();
    }

    @Transactional
    public void remove(StopEntity stop) {
        stopRepository.findById(stop.getId()).ifPresent(stopEntity -> {
            stopEntity.removeAllJourneys();
            stopRepository.delete(stopEntity);
        });
    }

    @Override
    protected Specification<StopEntity> getSpecification(String filterKey, String filterValue) {
        if (StringUtils.isEmpty(filterKey)){
            return null;
        }
        return StopSpecification.valueOf(filterKey).getSpecification(filterValue);
    }
}
