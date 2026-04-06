package com.myapp.Automation_Control_Service.controller;

import com.myapp.Automation_Control_Service.dto.AutomationLogDTO;
import com.myapp.Automation_Control_Service.dto.TelemetryDataDTO;
import com.myapp.Automation_Control_Service.service.AutomationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/automation")
@RequiredArgsConstructor
public class AutomationController {

    private final AutomationService automationService;

    @PostMapping("/process")
    public ResponseEntity<Void> processData(@RequestBody TelemetryDataDTO telemetryData) {
        automationService.processData(telemetryData);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/logs")
    public ResponseEntity<List<AutomationLogDTO>> getAllLogs() {
        List<AutomationLogDTO> logs = automationService.getAllLogs();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/logs/{zoneId}")
    public ResponseEntity<List<AutomationLogDTO>> getLogsByZone(@PathVariable String zoneId) {
        List<AutomationLogDTO> logs = automationService.getLogsByZone(zoneId);
        return ResponseEntity.ok(logs);
    }
}
