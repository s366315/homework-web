package org.hillel.web.service;

import org.hillel.web.persistence.entity.JourneyEntity;
import org.hillel.web.persistence.jpa.repository.JourneyJpaRepository;
import org.hillel.web.persistence.jpa.repository.specification.JourneySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class TransactionalJourneyService extends AbstractTransactionalService<JourneyEntity> {

    private final JourneyJpaRepository journeyRepository;

    @Autowired
    public TransactionalJourneyService(JourneyJpaRepository journeyRepository) {
        super(journeyRepository);
        this.journeyRepository = journeyRepository;
    }

    @Transactional
    public JourneyEntity createOrUpdate(final JourneyEntity entity){
        return journeyRepository.save(entity);
    }

    @Transactional
    public Optional<JourneyEntity> findById(Long id, boolean withDependencies) {
        final Optional<JourneyEntity> byId = journeyRepository.findById(id);
        if (withDependencies && byId.isPresent()){
            byId.get().getVehicle().getName();
            byId.get().getStops().size();
        }
        return byId;
    }

    @Transactional
    public void removeById(Long id) {
        journeyRepository.disableById(id);
    }

    @Transactional
    public void remove(JourneyEntity journey) {
        journeyRepository.delete(journey);
    }

    @Override
    protected Specification<JourneyEntity> getSpecification(String filterKey, String filterValue) {
        if (StringUtils.isEmpty(filterKey)){
            return null;
        }
        return JourneySpecification.valueOf(filterKey).getSpecification(filterValue);
    }
}
