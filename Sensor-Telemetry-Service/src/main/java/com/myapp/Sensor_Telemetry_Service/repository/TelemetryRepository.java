package com.myapp.Sensor_Telemetry_Service.repository;

import com.myapp.Sensor_Telemetry_Service.entity.TelemetryReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelemetryRepository extends JpaRepository<TelemetryReading, Long> {
    
    @Query(value = "SELECT * FROM telemetry_readings WHERE device_id = ?1 ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    Optional<TelemetryReading> findLatestByDeviceId(String deviceId);
}
