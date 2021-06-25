package org.hillel.web.controller.converter;

import org.hillel.web.controller.dto.VehicleDto;
import org.hillel.web.persistence.entity.SeatInfoEntity;
import org.hillel.web.persistence.entity.VehicleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Mapper
public interface VehicleMapper {

    DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                    .withZone(ZoneId.systemDefault());

    VehicleDto vehicleToDto(VehicleEntity vehicle);

    @Mapping(target = "createDate", ignore = true)
    VehicleEntity dtoToVehicle(VehicleDto dto);

    default VehicleDto fullVehicleToDto(VehicleEntity vehicle){
        VehicleDto vehicleDto = vehicleToDto(vehicle);
        vehicleDto.setJourneyCount(vehicle.getJourneys().size());
        vehicleDto.setFreeSeats(vehicle.getSeatInfos().stream().mapToInt(SeatInfoEntity::getFreeSeats).sum());
        return vehicleDto;
    }

    default String map(Instant value){
        return value != null ? FORMATTER.format(value) : null;
    }
}
