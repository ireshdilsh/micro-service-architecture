package com.myapp.Sensor_Telemetry_Service.client;

import com.myapp.Sensor_Telemetry_Service.dto.TelemetryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "automation-control-service")
public interface AutomationServiceClient {

    @PostMapping("/api/automation/process")
    void processData(@RequestBody TelemetryDTO telemetryDTO);
}
