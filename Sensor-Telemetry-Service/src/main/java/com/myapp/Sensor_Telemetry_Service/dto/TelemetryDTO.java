package com.myapp.Sensor_Telemetry_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelemetryDTO {
    private String deviceId;
    private String zoneId;
    private Double temperature;
    private String tempUnit;
    private Double humidity;
    private String humidityUnit;
    private String capturedAt;
}
