package com.myapp.Crop_Inventory_Service.dto;

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
public class CropDTO {

    private Long id;

    @NotBlank(message = "Crop name is required")
    private String name;

    @NotBlank(message = "Zone ID is required")
    private String zoneId;

    private String status;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    private String plantDate;
    private String harvestDate;
    private Long createdAt;
    private Long updatedAt;
}
