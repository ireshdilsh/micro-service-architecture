package com.myapp.Zone_Management_Service.service;

import com.myapp.Zone_Management_Service.client.IotIntegrationClient;
import com.myapp.Zone_Management_Service.dto.DeviceRegistrationRequest;
import com.myapp.Zone_Management_Service.dto.DeviceRegistrationResponse;
import com.myapp.Zone_Management_Service.dto.ZoneDTO;
import com.myapp.Zone_Management_Service.entity.Zone;
import com.myapp.Zone_Management_Service.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ZoneService {

    private final ZoneRepository zoneRepository;
    private final IotIntegrationClient iotIntegrationClient;

    public ZoneDTO createZone(ZoneDTO zoneDTO, String authToken) {
        // Validate temperature range
        if (zoneDTO.getMinTemp() >= zoneDTO.getMaxTemp()) {
            throw new IllegalArgumentException("minTemp must be strictly less than maxTemp");
        }

        // Register device with IoT API
        DeviceRegistrationRequest deviceRequest = DeviceRegistrationRequest.builder()
                .name(zoneDTO.getName())
                .zoneId(String.valueOf(zoneDTO.getId()))
                .build();

        try {
            DeviceRegistrationResponse deviceResponse = iotIntegrationClient.registerDevice(
                    deviceRequest,
                    authToken
            );
            zoneDTO.setDeviceId(deviceResponse.getDeviceId());
        } catch (Exception e) {
            log.warn("Failed to register device with IoT API: {}", e.getMessage());
            // Continue without device registration for now
        }

        Zone zone = Zone.builder()
                .name(zoneDTO.getName())
                .description(zoneDTO.getDescription())
                .minTemp(zoneDTO.getMinTemp())
                .maxTemp(zoneDTO.getMaxTemp())
                .minHumidity(zoneDTO.getMinHumidity())
                .maxHumidity(zoneDTO.getMaxHumidity())
                .deviceId(zoneDTO.getDeviceId())
                .build();

        Zone savedZone = zoneRepository.save(zone);
        log.info("Zone created with ID: {}", savedZone.getId());
        return convertToDTO(savedZone);
    }

    public ZoneDTO getZoneById(Long id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found with id: " + id));
        return convertToDTO(zone);
    }

    public List<ZoneDTO> getAllZones() {
        return zoneRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ZoneDTO updateZone(Long id, ZoneDTO zoneDTO) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found with id: " + id));

        // Validate temperature range
        if (zoneDTO.getMinTemp() >= zoneDTO.getMaxTemp()) {
            throw new IllegalArgumentException("minTemp must be strictly less than maxTemp");
        }

        zone.setName(zoneDTO.getName());
        zone.setDescription(zoneDTO.getDescription());
        zone.setMinTemp(zoneDTO.getMinTemp());
        zone.setMaxTemp(zoneDTO.getMaxTemp());
        zone.setMinHumidity(zoneDTO.getMinHumidity());
        zone.setMaxHumidity(zoneDTO.getMaxHumidity());

        Zone updatedZone = zoneRepository.save(zone);
        log.info("Zone updated with ID: {}", id);
        return convertToDTO(updatedZone);
    }

    public void deleteZone(Long id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found with id: " + id));
        zoneRepository.delete(zone);
        log.info("Zone deleted with ID: {}", id);
    }

    private ZoneDTO convertToDTO(Zone zone) {
        return ZoneDTO.builder()
                .id(zone.getId())
                .name(zone.getName())
                .description(zone.getDescription())
                .minTemp(zone.getMinTemp())
                .maxTemp(zone.getMaxTemp())
                .minHumidity(zone.getMinHumidity())
                .maxHumidity(zone.getMaxHumidity())
                .deviceId(zone.getDeviceId())
                .createdAt(zone.getCreatedAt())
                .updatedAt(zone.getUpdatedAt())
                .build();
    }
}
