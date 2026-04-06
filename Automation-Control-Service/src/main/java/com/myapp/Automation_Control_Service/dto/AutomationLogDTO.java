package com.myapp.Automation_Control_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutomationLogDTO {
    private Long id;
    private String zoneId;
    private String action;
    private Double temperature;
    private Double humidity;
    private String rule;
    private Long createdAt;
}
