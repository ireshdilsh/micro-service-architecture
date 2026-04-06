package com.myapp.Zone_Management_Service.client;

import com.myapp.Zone_Management_Service.dto.DeviceRegistrationRequest;
import com.myapp.Zone_Management_Service.dto.DeviceRegistrationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "iot-integration-service", url = "http://104.211.95.241:8080/api")
public interface IotIntegrationClient {

    @PostMapping("/devices")
    DeviceRegistrationResponse registerDevice(
            @RequestBody DeviceRegistrationRequest request,
            @RequestHeader("Authorization") String authToken
    );
}
