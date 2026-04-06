package com.myapp.Sensor_Telemetry_Service.controller;

import com.myapp.Sensor_Telemetry_Service.dto.TelemetryDTO;
import com.myapp.Sensor_Telemetry_Service.service.SensorTelemetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorTelemetryController {

    private final SensorTelemetryService sensorTelemetryService;

    @GetMapping("/latest/{deviceId}")
    public ResponseEntity<TelemetryDTO> getLatestReading(@PathVariable String deviceId) {
        TelemetryDTO telemetry = sensorTelemetryService.getLatestReading(deviceId);
        return ResponseEntity.ok(telemetry);
    }
}
