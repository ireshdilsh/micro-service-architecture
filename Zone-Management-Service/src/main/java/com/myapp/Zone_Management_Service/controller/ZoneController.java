package com.myapp.Zone_Management_Service.controller;

import com.myapp.Zone_Management_Service.dto.ZoneDTO;
import com.myapp.Zone_Management_Service.service.ZoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    @PostMapping
    public ResponseEntity<ZoneDTO> createZone(
            @Valid @RequestBody ZoneDTO zoneDTO,
            @RequestHeader(value = "Authorization", required = false) String authToken) {
        ZoneDTO createdZone = zoneService.createZone(zoneDTO, authToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdZone);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZoneDTO> getZoneById(@PathVariable Long id) {
        ZoneDTO zone = zoneService.getZoneById(id);
        return ResponseEntity.ok(zone);
    }

    @GetMapping
    public ResponseEntity<List<ZoneDTO>> getAllZones() {
        List<ZoneDTO> zones = zoneService.getAllZones();
        return ResponseEntity.ok(zones);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZoneDTO> updateZone(
            @PathVariable Long id,
            @Valid @RequestBody ZoneDTO zoneDTO) {
        ZoneDTO updatedZone = zoneService.updateZone(id, zoneDTO);
        return ResponseEntity.ok(updatedZone);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable Long id) {
        zoneService.deleteZone(id);
        return ResponseEntity.noContent().build();
    }
}
