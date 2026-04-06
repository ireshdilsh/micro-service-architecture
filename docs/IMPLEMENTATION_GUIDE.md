# AGMS Implementation Summary

## Project Structure

```
micro-service-architecture/
├── service-registery/              # Eureka Server (Port 8761)
├── API-Gateway/                    # Spring Cloud Gateway (Port 8080)
├── Config-Server/                  # Spring Cloud Config Server (Port 8888)
├── Zone-Management-Service/        # Zone API (Port 8081)
├── Sensor-Telemetry-Service/       # Telemetry API (Port 8082)
├── Automation-Control-Service/     # Automation Rule Engine (Port 8083)
├── Crop-Inventory-Service/         # Crop Management API (Port 8084)
├── README.md                       # Complete setup guide
├── AGMS-API-Collection.json        # Postman collection
└── docs/                           # Documentation
```

## Key Implementation Details

### 1. Service Discovery (Eureka)
- **Port**: 8761
- All microservices register with Eureka on startup
- Eureka Dashboard: http://localhost:8761
- Services: `@EnableDiscoveryClient` annotation

### 2. API Gateway
- **Port**: 8080
- Routes requests to backend services using load-balanced URLs (lb://)
- JWT token validation in `JwtAuthenticationFilter`
- Routes configured in `application.properties`

### 3. Config Server
- **Port**: 8888
- Uses native file system (local directory) for configuration
- Services fetch config on startup
- bootstrap.yml enables Config Client functionality

### 4. Zone Management Service (Port 8081)
**Entities**: Zone
**DTOs**: ZoneDTO, DeviceRegistrationRequest, DeviceRegistrationResponse
**Features**:
- CRUD operations for zones
- Temperature/Humidity threshold validation
- IoT device registration via Feign client
- @EnableFeignClients for inter-service communication

**API Endpoints**:
- `POST /api/zones` - Create zone
- `GET /api/zones/{id}` - Get zone
- `GET /api/zones` - List all zones
- `PUT /api/zones/{id}` - Update zone
- `DELETE /api/zones/{id}` - Delete zone

### 5. Sensor Telemetry Service (Port 8082)
**Entities**: TelemetryReading
**DTOs**: TelemetryDTO, IotTelemetryResponse
**Features**:
- Scheduled task (every 10 seconds) for telemetry fetching
- Integration with external IoT API
- Feign client for IoT device telemetry
- Pushes data to Automation Service

**API Endpoints**:
- `GET /api/sensors/latest/{deviceId}` - Get latest reading

### 6. Automation Control Service (Port 8083)
**Entities**: AutomationLog
**DTOs**: TelemetryDataDTO, AutomationLogDTO, ZoneThresholdsDTO
**Features**:
- Rule engine for automated decision making
- Fetches zone thresholds via Feign client
- Logs triggered actions

**Automation Rules**:
- If `temperature > maxTemp` → TURN_FAN_ON
- If `temperature < minTemp` → TURN_HEATER_ON
- If `humidity > maxHumidity` → TURN_DEHUMIDIFIER_ON
- If `humidity < minHumidity` → TURN_HUMIDIFIER_ON

**API Endpoints**:
- `POST /api/automation/process` - Process telemetry data (internal)
- `GET /api/automation/logs` - Get all automation logs
- `GET /api/automation/logs/{zoneId}` - Get zone-specific logs

### 7. Crop Inventory Service (Port 8084)
**Entities**: Crop (with enum CropStatus: SEEDLING, VEGETATIVE, HARVESTED)
**DTOs**: CropDTO
**Features**:
- Crop batch management
- Lifecycle state machine (SEEDLING → VEGETATIVE → HARVESTED)
- Zone-based crop tracking

**API Endpoints**:
- `POST /api/crops` - Create crop batch
- `GET /api/crops/{id}` - Get crop details
- `GET /api/crops` - List all crops
- `GET /api/crops/zone/{zoneId}` - Get crops by zone
- `PUT /api/crops/{id}/status` - Update crop status
- `DELETE /api/crops/{id}` - Delete crop

## Security Implementation

### JWT Authentication (API Gateway Level)
- `JwtTokenProvider` class: Token validation and generation
- `JwtAuthenticationFilter`: Global filter for request authentication
- Validates Bearer tokens on all external requests
- Internal service-to-service communication bypasses gateway authentication

## Inter-Service Communication

### Feign Clients Implemented:
1. **Zone Service** → IoT Integration Client
   - Registers devices with external IoT API

2. **Sensor Service** → Automation Service Client
   - Sends telemetry data for processing

3. **Automation Service** → Zone Service Client
   - Fetches zone thresholds for rule evaluation

## Database Configuration

All services use H2 in-memory database:
- **Driver**: org.h2.Driver
- **Auto-Migration**: JPA with `ddl-auto=create-drop`
- **H2 Console**: Enabled on each service

## Configuration Management

### Bootstrap Configuration
- `bootstrap.yml` configures Config Client
- Specifies Config Server URI: http://localhost:8888
- Retry logic for resilience

### Application Properties
- Service name and port
- Eureka registration settings
- Database configurations
- External API endpoints

## Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Framework | Spring Boot | 4.0.3 |
| Cloud Platform | Spring Cloud | 2025.1.0 |
| JDK | Java | 17 |
| Service Discovery | Netflix Eureka | - |
| API Gateway | Spring Cloud Gateway | - |
| Configuration | Spring Cloud Config | - |
| RPC | OpenFeign | - |
| Database | H2 | Runtime |
| ORM | Spring Data JPA | - |
| Security | JWT (jjwt) | 0.12.3 |
| Dependency Injection | Lombok | - |

## Testing Workflow

1. **Start Infrastructure** (in order):
   - Service Registry (8761)
   - Config Server (8888)
   - API Gateway (8080)

2. **Start Domain Services** (any order):
   - Zone Service (8081)
   - Sensor Service (8082)
   - Automation Service (8083)
   - Crop Service (8084)

3. **Verify Health**:
   - Check Eureka: http://localhost:8761
   - All services should show Status: UP

4. **Test APIs**:
   - Import Postman collection
   - Set environment variables (base_url, token)
   - Execute requests

## Deployment Checklist

- [ ] All 7 services start without errors
- [ ] Eureka dashboard shows all services UP
- [ ] Gateway routes requests correctly
- [ ] JWT authentication works
- [ ] Inter-service communication via Feign succeeds
- [ ] H2 databases initialize correctly
- [ ] Config Server loads configurations
- [ ] All API endpoints respond with correct data

## Known Limitations & Future Improvements

1. **Authentication**: Current JWT implementation is basic; implement OAuth2/Spring Security
2. **Database**: H2 in-memory; upgrade to PostgreSQL/MySQL for persistence
3. **Message Queue**: Consider Kafka for async event processing
4. **Circuit Breaker**: Implement Hystrix for resilience
5. **Logging**: Add centralized logging (ELK stack)
6. **Monitoring**: Add metrics with Micrometer and Prometheus

## Troubleshooting Guide

### Service not registering with Eureka
- Check if Service Registry is running on port 8761
- Verify eureka.client.service-url.defaultZone in application.properties
- Check firewall settings

### Config Server connection timeout
- Ensure Config Server is running on port 8888
- Verify config-repo directory exists and is accessible
- Check file system permissions

### Feign client failures
- Ensure target service is running and registered with Eureka
- Check inter-service URLs use service names (lb://)
- Verify Content-Type headers in Feign clients

### JWT validation failures
- Ensure token is in "Bearer <token>" format
- Check token expiration time
- Verify jwt.secret matches between services

## References

- Spring Cloud Documentation: https://spring.io/cloud
- Eureka Documentation: https://github.com/Netflix/eureka
- OpenFeign Documentation: https://spring.cloud.io/spring-cloud-openfeign
- Spring Cloud Gateway: https://spring.cloud.io/spring-cloud-gateway
