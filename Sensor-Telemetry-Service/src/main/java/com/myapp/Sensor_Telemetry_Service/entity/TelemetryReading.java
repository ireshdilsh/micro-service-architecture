package com.myapp.Sensor_Telemetry_Service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "telemetry_readings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelemetryReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceId;
    private String zoneId;
    private Double temperature;
    private String tempUnit;
    private Double humidity;
    private String humidityUnit;

    @Column(name = "captured_at")
    private String capturedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Long createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = System.currentTimeMillis();
    }
}
