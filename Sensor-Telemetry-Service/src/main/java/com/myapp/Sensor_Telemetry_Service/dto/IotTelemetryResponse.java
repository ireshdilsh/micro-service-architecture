package com.myapp.Sensor_Telemetry_Service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IotTelemetryResponse {
    private String deviceId;
    private String zoneId;
    
    @JsonProperty("value")
    private SensorValue value;
    
    private String capturedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SensorValue {
        private Double temperature;
        private String tempUnit;
        private Double humidity;
        private String humidityUnit;
    }
}
