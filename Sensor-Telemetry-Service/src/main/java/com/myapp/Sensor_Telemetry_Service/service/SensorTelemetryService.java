package com.myapp.Sensor_Telemetry_Service.service;

import com.myapp.Sensor_Telemetry_Service.client.IotTelemetryClient;
import com.myapp.Sensor_Telemetry_Service.dto.IotTelemetryResponse;
import com.myapp.Sensor_Telemetry_Service.dto.TelemetryDTO;
import com.myapp.Sensor_Telemetry_Service.entity.TelemetryReading;
import com.myapp.Sensor_Telemetry_Service.repository.TelemetryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorTelemetryService {

    private final TelemetryRepository telemetryRepository;
    private final IotTelemetryClient iotTelemetryClient;
    private final RestTemplate restTemplate;
    private final AutomationServiceClient automationServiceClient;

    @Value("${iot.api.auth.username:testuser}")
    private String iotUsername;

    @Value("${iot.api.auth.password:testpass}")
    private String iotPassword;

    private String cachedAuthToken;
    private Long tokenExpiry;

    public TelemetryDTO getLatestReading(String deviceId) {
        TelemetryReading reading = telemetryRepository.findLatestByDeviceId(deviceId)
                .orElseThrow(() -> new RuntimeException("No telemetry found for device: " + deviceId));
        return convertToDTO(reading);
    }

    @Scheduled(fixedDelay = 10000) // Run every 10 seconds
    public void fetchAndProcessTelemetry() {
        log.info("Starting scheduled telemetry fetch...");
        // This method will fetch from IoT API and send to Automation Service
        // In production, we would iterate through all active devices
        try {
            String authToken = getAuthToken();
            // Fetch telemetry for known devices
            // For demo, we'll process data when it comes from the API
            log.debug("Telemetry fetch cycle completed");
        } catch (Exception e) {
            log.error("Error fetching telemetry: {}", e.getMessage(), e);
        }
    }

    public void saveTelemetryReading(IotTelemetryResponse response) {
        TelemetryReading reading = TelemetryReading.builder()
                .deviceId(response.getDeviceId())
                .zoneId(response.getZoneId())
                .temperature(response.getValue().getTemperature())
                .tempUnit(response.getValue().getTempUnit())
                .humidity(response.getValue().getHumidity())
                .humidityUnit(response.getValue().getHumidityUnit())
                .capturedAt(response.getCapturedAt())
                .build();

        TelemetryReading savedReading = telemetryRepository.save(reading);
        log.info("Telemetry saved for device: {}", response.getDeviceId());

        // Send to Automation Service for processing
        try {
            automationServiceClient.processData(convertToDTO(savedReading));
        } catch (Exception e) {
            log.error("Failed to send telemetry to automation service: {}", e.getMessage());
        }
    }

    private String getAuthToken() {
        // In production, implement proper token management
        // For now, return a placeholder
        return "Bearer test-token";
    }

    private TelemetryDTO convertToDTO(TelemetryReading reading) {
        return TelemetryDTO.builder()
                .deviceId(reading.getDeviceId())
                .zoneId(reading.getZoneId())
                .temperature(reading.getTemperature())
                .tempUnit(reading.getTempUnit())
                .humidity(reading.getHumidity())
                .humidityUnit(reading.getHumidityUnit())
                .capturedAt(reading.getCapturedAt())
                .build();
    }
}
