package com.myapp.Crop_Inventory_Service.service;

import com.myapp.Crop_Inventory_Service.dto.CropDTO;
import com.myapp.Crop_Inventory_Service.entity.Crop;
import com.myapp.Crop_Inventory_Service.repository.CropRepository;
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
public class CropService {

    private final CropRepository cropRepository;

    public CropDTO createCrop(CropDTO cropDTO) {
        Crop crop = Crop.builder()
                .name(cropDTO.getName())
                .zoneId(cropDTO.getZoneId())
                .quantity(cropDTO.getQuantity())
                .plantDate(cropDTO.getPlantDate())
                .harvestDate(cropDTO.getHarvestDate())
                .status(Crop.CropStatus.SEEDLING)
                .build();

        Crop savedCrop = cropRepository.save(crop);
        log.info("Crop created with ID: {}", savedCrop.getId());
        return convertToDTO(savedCrop);
    }

    public CropDTO getCropById(Long id) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crop not found with id: " + id));
        return convertToDTO(crop);
    }

    public List<CropDTO> getAllCrops() {
        return cropRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CropDTO> getCropsByZone(String zoneId) {
        return cropRepository.findByZoneId(zoneId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CropDTO updateCropStatus(Long id, String status) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crop not found with id: " + id));

        try {
            crop.setStatus(Crop.CropStatus.valueOf(status.toUpperCase()));
            Crop updatedCrop = cropRepository.save(crop);
            log.info("Crop {} status updated to: {}", id, status);
            return convertToDTO(updatedCrop);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }
    }

    public void deleteCrop(Long id) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crop not found with id: " + id));
        cropRepository.delete(crop);
        log.info("Crop deleted with ID: {}", id);
    }

    private CropDTO convertToDTO(Crop crop) {
        return CropDTO.builder()
                .id(crop.getId())
                .name(crop.getName())
                .zoneId(crop.getZoneId())
                .status(crop.getStatus().toString())
                .quantity(crop.getQuantity())
                .plantDate(crop.getPlantDate())
                .harvestDate(crop.getHarvestDate())
                .createdAt(crop.getCreatedAt())
                .updatedAt(crop.getUpdatedAt())
                .build();
    }
}
