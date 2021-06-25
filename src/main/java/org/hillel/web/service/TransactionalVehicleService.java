package org.hillel.web.service;

import org.hillel.web.persistence.entity.VehicleEntity;
import org.hillel.web.persistence.jpa.repository.SimpleVehicleDto;
import org.hillel.web.persistence.jpa.repository.VehicleJpaRepository;
import org.hillel.web.persistence.jpa.repository.specification.VehicleSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionalVehicleService extends AbstractTransactionalService<VehicleEntity>{

    private final VehicleJpaRepository vehicleRepository;

    @Autowired
    public TransactionalVehicleService(VehicleJpaRepository vehicleRepository) {
        super(vehicleRepository);
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    public VehicleEntity createOrUpdate(VehicleEntity vehicle){
        return vehicleRepository.save(vehicle);
    }

    @Transactional
    public void remove(VehicleEntity vehicle){
        vehicleRepository.findById(vehicle.getId()).ifPresent(vehicleEntity -> {
            vehicleEntity.removeAllJourney();
            vehicleRepository.deleteSeatInfoByVehicle(vehicleEntity);
            vehicleRepository.delete(vehicleEntity);
        });
    }

    @Transactional
    public void removeById(Long id){
        vehicleRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Collection<VehicleEntity> findByIds(Long... ids){
        return vehicleRepository.findAllById(Arrays.asList(ids));
    }

    @Transactional(readOnly = true)
    public Optional<VehicleEntity> findById(Long id, boolean withDep){
        final Optional<VehicleEntity> vehicle = vehicleRepository.findById(id);
        if (!vehicle.isPresent()) return vehicle;
        if (!withDep) return vehicle;
        vehicle.get().getJourneys().size();
        vehicle.get().getSeatInfos().size();
        return vehicle;
    }

    @Transactional(readOnly = true)
    public Collection<VehicleEntity> findAllByName(String name){
        return vehicleRepository.findAll(VehicleSpecification.NAME.getSpecification(name)
                .and(VehicleSpecification.ONLY_ACTIVE.getSpecification(null)));
    }

    @Transactional(readOnly = true)
    public Collection<VehicleEntity> findWithMinFreeSeats(){
        return vehicleRepository.findWithMinFreeSeats();
    }

    @Transactional(readOnly = true)
    public Collection<VehicleEntity> findWithMaxFreeSeats(){
        return vehicleRepository.findWithMaxFreeSeats();
    }

    @Transactional
    public void disableById(Long id){
        vehicleRepository.disableById(id);
    }

    @Transactional
    public List<SimpleVehicleDto> listAllSimpleVehicles(){
        return vehicleRepository.findAllByActiveIsTrue();
    }

    @Override
    protected Specification<VehicleEntity> getSpecification(String filterKey, String filterValue) {
        if (StringUtils.isEmpty(filterKey)){
            return null;
        }
        return VehicleSpecification.valueOf(filterKey).getSpecification(filterValue);
    }
}
