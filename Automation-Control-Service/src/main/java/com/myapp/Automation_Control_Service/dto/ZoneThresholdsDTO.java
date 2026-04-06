package com.myapp.Automation_Control_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZoneThresholdsDTO {
    private Long id;
    private String name;
    private Double minTemp;
    private Double maxTemp;
    private Double minHumidity;
    private Double maxHumidity;
}
