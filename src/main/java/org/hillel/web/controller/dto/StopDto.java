package org.hillel.web.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StopDto {

    private Long id;
    private String name;
    private String active;
    private String createDate;
    private String description;

    private Double longitude;
    private Double latitude;
}