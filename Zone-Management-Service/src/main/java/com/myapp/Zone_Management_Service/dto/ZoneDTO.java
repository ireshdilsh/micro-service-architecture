package com.myapp.Zone_Management_Service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZoneDTO {

    private Long id;

    @NotBlank(message = "Zone name is required")
    private String name;

    @NotBlank(message = "Zone description is required")
    private String description;

    @NotNull(message = "Min temperature is required")
    private Double minTemp;

    @NotNull(message = "Max temperature is required")
    private Double maxTemp;

    @NotNull(message = "Min humidity is required")
    private Double minHumidity;

    @NotNull(message = "Max humidity is required")
    private Double maxHumidity;

    private String deviceId;
    private Long createdAt;
    private Long updatedAt;
}
