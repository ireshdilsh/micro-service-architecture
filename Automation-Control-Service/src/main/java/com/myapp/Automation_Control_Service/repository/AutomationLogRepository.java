package com.myapp.Automation_Control_Service.repository;

import com.myapp.Automation_Control_Service.entity.AutomationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutomationLogRepository extends JpaRepository<AutomationLog, Long> {
    List<AutomationLog> findByZoneId(String zoneId);
}
