package com.myapp.Automation_Control_Service.service;

import com.myapp.Automation_Control_Service.client.ZoneServiceClient;
import com.myapp.Automation_Control_Service.dto.AutomationLogDTO;
import com.myapp.Automation_Control_Service.dto.TelemetryDataDTO;
import com.myapp.Automation_Control_Service.dto.ZoneThresholdsDTO;
import com.myapp.Automation_Control_Service.entity.AutomationLog;
import com.myapp.Automation_Control_Service.repository.AutomationLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AutomationService {

    private final AutomationLogRepository automationLogRepository;
    private final ZoneServiceClient zoneServiceClient;

    public void processData(TelemetryDataDTO telemetryData) {
        log.info("Processing telemetry data for zone: {}", telemetryData.getZoneId());

        try {
            // Fetch zone thresholds
            Long zoneId = Long.parseLong(telemetryData.getZoneId());
            ZoneThresholdsDTO zone = zoneServiceClient.getZoneThresholds(zoneId);

            // Apply rules
            List<String> triggeredActions = new ArrayList<>();

            if (telemetryData.getTemperature() > zone.getMaxTemp()) {
                triggeredActions.add("TURN_FAN_ON");
                log.warn("Temperature exceeds max threshold for zone {}: {} > {}",
                        zoneId, telemetryData.getTemperature(), zone.getMaxTemp());
            }

            if (telemetryData.getTemperature() < zone.getMinTemp()) {
                triggeredActions.add("TURN_HEATER_ON");
                log.warn("Temperature below min threshold for zone {}: {} < {}",
                        zoneId, telemetryData.getTemperature(), zone.getMinTemp());
            }

            if (telemetryData.getHumidity() > zone.getMaxHumidity()) {
                triggeredActions.add("TURN_DEHUMIDIFIER_ON");
                log.warn("Humidity exceeds max threshold for zone {}: {} > {}",
                        zoneId, telemetryData.getHumidity(), zone.getMaxHumidity());
            }

            if (telemetryData.getHumidity() < zone.getMinHumidity()) {
                triggeredActions.add("TURN_HUMIDIFIER_ON");
                log.warn("Humidity below min threshold for zone {}: {} < {}",
                        zoneId, telemetryData.getHumidity(), zone.getMinHumidity());
            }

            // Log all triggered actions
            for (String action : triggeredActions) {
                AutomationLog log = AutomationLog.builder()
                        .zoneId(telemetryData.getZoneId())
                        .action(action)
                        .temperature(telemetryData.getTemperature())
                        .humidity(telemetryData.getHumidity())
                        .rule(buildRuleString(telemetryData, zone, action))
                        .build();
                automationLogRepository.save(log);
            }

        } catch (Exception e) {
            log.error("Error processing automation rules for zone {}: {}",
                    telemetryData.getZoneId(), e.getMessage(), e);
        }
    }

    public List<AutomationLogDTO> getAllLogs() {
        return automationLogRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AutomationLogDTO> getLogsByZone(String zoneId) {
        return automationLogRepository.findByZoneId(zoneId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private String buildRuleString(TelemetryDataDTO telemetry, ZoneThresholdsDTO zone, String action) {
        StringBuilder rule = new StringBuilder();
        
        if (action.equals("TURN_FAN_ON")) {
            rule.append(String.format("IF temperature (%.2f) > maxTemp (%.2f) THEN TURN_FAN_ON",
                    telemetry.getTemperature(), zone.getMaxTemp()));
        } else if (action.equals("TURN_HEATER_ON")) {
            rule.append(String.format("IF temperature (%.2f) < minTemp (%.2f) THEN TURN_HEATER_ON",
                    telemetry.getTemperature(), zone.getMinTemp()));
        } else if (action.equals("TURN_DEHUMIDIFIER_ON")) {
            rule.append(String.format("IF humidity (%.2f) > maxHumidity (%.2f) THEN TURN_DEHUMIDIFIER_ON",
                    telemetry.getHumidity(), zone.getMaxHumidity()));
        } else if (action.equals("TURN_HUMIDIFIER_ON")) {
            rule.append(String.format("IF humidity (%.2f) < minHumidity (%.2f) THEN TURN_HUMIDIFIER_ON",
                    telemetry.getHumidity(), zone.getMinHumidity()));
        }
        
        return rule.toString();
    }

    private AutomationLogDTO convertToDTO(AutomationLog log) {
        return AutomationLogDTO.builder()
                .id(log.getId())
                .zoneId(log.getZoneId())
                .action(log.getAction())
                .temperature(log.getTemperature())
                .humidity(log.getHumidity())
                .rule(log.getRule())
                .createdAt(log.getCreatedAt())
                .build();
    }
}
