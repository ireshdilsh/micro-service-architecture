package com.myapp.Zone_Management_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceRegistrationResponse {
    private String deviceId;
    private String name;
    private String zoneId;
    private String userId;
    private String createdAt;
}
