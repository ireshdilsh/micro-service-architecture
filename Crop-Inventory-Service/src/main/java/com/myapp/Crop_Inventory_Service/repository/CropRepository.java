package com.myapp.Crop_Inventory_Service.repository;

import com.myapp.Crop_Inventory_Service.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {
    List<Crop> findByZoneId(String zoneId);
}
