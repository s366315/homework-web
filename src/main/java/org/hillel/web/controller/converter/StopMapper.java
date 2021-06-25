package org.hillel.web.controller.converter;

import org.hillel.web.controller.dto.StopDto;
import org.hillel.web.persistence.entity.StopEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Mapper
public interface StopMapper {

    DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                    .withZone(ZoneId.systemDefault());

    @Mapping(target = "name", source = "commonInfo.name")
    @Mapping(target = "description", source = "commonInfo.description")
    StopDto stopToDto(StopEntity stop);

    default StopDto fullStopToDto(StopEntity stop){
        StopDto stopDto = stopToDto(stop);
        stopDto.setLatitude(stop.getStopAddInfo().getLatitude());
        stopDto.setLongitude(stop.getStopAddInfo().getLongitude());
        return stopDto;
    }

    default String map(Instant value){
        return value != null ? FORMATTER.format(value) : null;
    }
}
