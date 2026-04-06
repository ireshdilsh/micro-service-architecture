package com.myapp.Crop_Inventory_Service.controller;

import com.myapp.Crop_Inventory_Service.dto.CropDTO;
import com.myapp.Crop_Inventory_Service.service.CropService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
@RequiredArgsConstructor
public class CropController {

    private final CropService cropService;

    @PostMapping
    public ResponseEntity<CropDTO> createCrop(@Valid @RequestBody CropDTO cropDTO) {
        CropDTO createdCrop = cropService.createCrop(cropDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCrop);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CropDTO> getCropById(@PathVariable Long id) {
        CropDTO crop = cropService.getCropById(id);
        return ResponseEntity.ok(crop);
    }

    @GetMapping
    public ResponseEntity<List<CropDTO>> getAllCrops() {
        List<CropDTO> crops = cropService.getAllCrops();
        return ResponseEntity.ok(crops);
    }

    @GetMapping("/zone/{zoneId}")
    public ResponseEntity<List<CropDTO>> getCropsByZone(@PathVariable String zoneId) {
        List<CropDTO> crops = cropService.getCropsByZone(zoneId);
        return ResponseEntity.ok(crops);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<CropDTO> updateCropStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        CropDTO updatedCrop = cropService.updateCropStatus(id, status);
        return ResponseEntity.ok(updatedCrop);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable Long id) {
        cropService.deleteCrop(id);
        return ResponseEntity.noContent().build();
    }
}
