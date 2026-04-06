package com.myapp.Sensor_Telemetry_Service.client;

import com.myapp.Sensor_Telemetry_Service.dto.IotTelemetryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "iot-telemetry-client", url = "http://104.211.95.241:8080/api")
public interface IotTelemetryClient {

    @GetMapping("/devices/telemetry/{deviceId}")
    IotTelemetryResponse getDeviceTelemetry(
            @PathVariable("deviceId") String deviceId,
            @RequestHeader("Authorization") String authToken
    );
}
