package com.myapp.Zone_Management_Service.repository;

import com.myapp.Zone_Management_Service.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {
    Optional<Zone> findByName(String name);
    Optional<Zone> findByDeviceId(String deviceId);
}
