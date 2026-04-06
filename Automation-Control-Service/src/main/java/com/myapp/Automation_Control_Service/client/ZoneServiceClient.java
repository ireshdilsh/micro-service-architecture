package com.myapp.Automation_Control_Service.client;

import com.myapp.Automation_Control_Service.dto.ZoneThresholdsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "zone-management-service")
public interface ZoneServiceClient {

    @GetMapping("/api/zones/{id}")
    ZoneThresholdsDTO getZoneThresholds(@PathVariable("id") Long id);
}
