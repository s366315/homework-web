package org.hillel.web.controller.converter;


import org.hillel.web.controller.dto.JourneyDto;
import org.hillel.web.persistence.entity.JourneyEntity;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Mapper
public interface JourneyMapper {

    DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                    .withZone(ZoneId.systemDefault());

    JourneyDto journeyToDto(JourneyEntity vehicle);

    default JourneyDto fullJourneyToDto(JourneyEntity journey){
        JourneyDto journeyDto = journeyToDto(journey);
        journeyDto.setVehicleName(journey.getVehicle().getName());
        journeyDto.setStopsCount(journey.getStops().size());
        return journeyDto;
    }

    default String map(Instant value){
        return value != null ? FORMATTER.format(value) : null;
    }
}
