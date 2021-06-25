package org.hillel.web.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDto {

    private Long id;
    private String name;
    private String createDate;

    private int journeyCount;
    private int freeSeats;
}
