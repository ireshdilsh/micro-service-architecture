package com.myapp.Crop_Inventory_Service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "crops")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Crop name is required")
    private String name;

    @NotBlank(message = "Zone ID is required")
    private String zoneId;

    @Enumerated(EnumType.STRING)
    private CropStatus status;

    private Integer quantity;
    private String plantDate;
    private String harvestDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        if (this.status == null) {
            this.status = CropStatus.SEEDLING;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }

    public enum CropStatus {
        SEEDLING, VEGETATIVE, HARVESTED
    }
}
