package com.myapp.Automation_Control_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelemetryDataDTO {
    private String deviceId;
    private String zoneId;
    private Double temperature;
    private String tempUnit;
    private Double humidity;
    private String humidityUnit;
    private String capturedAt;
}
