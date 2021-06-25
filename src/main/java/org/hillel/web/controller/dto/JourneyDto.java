package org.hillel.web.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.hillel.web.persistence.entity.enums.DirectionType;

@Getter
@Setter
public class JourneyDto {

    private Long id;
    private String stationFrom;
    private String stationTo;
    private String dateFrom;
    private String dateTo;
    private DirectionType direction;
    private String createDate;

    private String vehicleName;
    private int stopsCount;

}
