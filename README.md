# Automated Greenhouse Management System (AGMS)

A cloud-native, microservice-based platform for managing automated greenhouses with real-time sensor data integration and intelligent control systems.

## Architecture Overview

The AGMS comprises a distributed system of microservices built on Spring Cloud, with the following architecture:

### Infrastructure Services
- **Service Registry (Eureka)** - Port 8761: Dynamic service registration and discovery
- **API Gateway** - Port 8080: Single entry point for all external requests with JWT authentication
- **Config Server** - Port 8888: Centralized configuration management

### Domain Microservices
- **Zone Management Service** - Port 8081: Manage greenhouse zones and environmental thresholds
- **Sensor Telemetry Service** - Port 8082: Fetch real-time sensor data from external IoT API
- **Automation & Control Service** - Port 8083: Rule engine for automated control decisions
- **Crop Inventory Service** - Port 8084: Track crop lifecycle and inventory

## Prerequisites

- Java 17 or higher
- Maven 3.9+
- Git
- Postman (for API testing)

## Setup & Startup Instructions

### Step 1: Configure Local File System for Config Server

Create the config repository directory and configuration files:

```bash
mkdir -p d:/micro-service-architecture/config-repo
```

Create config files for each service in `config-repo/`:

**zone-management-service.properties:**
```properties
spring.datasource.url=jdbc:h2:mem:zonedb
spring.jpa.hibernate.ddl-auto=create-drop
iot.api.base-url=http://104.211.95.241:8080/api
```

**sensor-telemetry-service.properties:**
```properties
spring.datasource.url=jdbc:h2:mem:telemetrydb
spring.jpa.hibernate.ddl-auto=create-drop
iot.api.auth.username=testuser
iot.api.auth.password=testpass
```

**automation-control-service.properties:**
```properties
spring.datasource.url=jdbc:h2:mem:automationdb
spring.jpa.hibernate.ddl-auto=create-drop
```

**crop-inventory-service.properties:**
```properties
spring.datasource.url=jdbc:h2:mem:cropdb
spring.jpa.hibernate.ddl-auto=create-drop
```

### Step 2: Start Infrastructure Services (In Order)

Open separate terminals for each service:

#### Terminal 1: Start Service Registry (Eureka)
```bash
cd service-registery
mvn spring-boot:run
# Eureka Dashboard: http://localhost:8761
```

#### Terminal 2: Start Config Server
```bash
cd Config-Server
mvn spring-boot:run
```

#### Terminal 3: Start API Gateway
```bash
cd API-Gateway
mvn spring-boot:run
```

### Step 3: Start Domain Microservices (In Any Order)

#### Terminal 4: Start Zone Management Service
```bash
cd Zone-Management-Service
mvn spring-boot:run
```

#### Terminal 5: Start Sensor Telemetry Service
```bash
cd Sensor-Telemetry-Service
mvn spring-boot:run
```

#### Terminal 6: Start Automation Control Service
```bash
cd Automation-Control-Service
mvn spring-boot:run
```

#### Terminal 7: Start Crop Inventory Service
```bash
cd Crop-Inventory-Service
mvn spring-boot:run
```

### Verification

All services should be registered in the Eureka dashboard:
- **URL:** http://localhost:8761
- You should see all services with **Status: UP**

## API Documentation

### Authentication

All API requests (except service-to-service) require a Bearer Token in the Authorization header:

```
Authorization: Bearer <token>
```

For testing, use a valid JWT token or generate one for testing purposes.

### Zone Management Service APIs

#### Create Zone
```
POST /api/zones
Content-Type: application/json
Authorization: Bearer <token>

{
  "name": "Tomato Zone",
  "description": "Zone for tomato cultivation",
  "minTemp": 20.0,
  "maxTemp": 28.0,
  "minHumidity": 60.0,
  "maxHumidity": 80.0
}
```

#### Get Zone by ID
```
GET /api/zones/{id}
Authorization: Bearer <token>
```

#### Get All Zones
```
GET /api/zones
Authorization: Bearer <token>
```

#### Update Zone
```
PUT /api/zones/{id}
Content-Type: application/json
Authorization: Bearer <token>

{
  "name": "Updated Zone Name",
  "description": "Updated description",
  "minTemp": 19.0,
  "maxTemp": 29.0,
  "minHumidity": 55.0,
  "maxHumidity": 85.0
}
```

#### Delete Zone
```
DELETE /api/zones/{id}
Authorization: Bearer <token>
```

### Sensor Telemetry Service APIs

#### Get Latest Telemetry Reading
```
GET /api/sensors/latest/{deviceId}
Authorization: Bearer <token>
```

Response:
```json
{
  "deviceId": "device-123",
  "zoneId": "1",
  "temperature": 25.5,
  "tempUnit": "CELSIUS",
  "humidity": 72.3,
  "humidityUnit": "PERCENTAGE",
  "capturedAt": "2026-02-22T10:30:00Z"
}
```

### Automation Control Service APIs

#### Process Telemetry Data (Internal)
```
POST /api/automation/process
Content-Type: application/json

{
  "deviceId": "device-123",
  "zoneId": "1",
  "temperature": 32.5,
  "tempUnit": "CELSIUS",
  "humidity": 45.0,
  "humidityUnit": "PERCENTAGE",
  "capturedAt": "2026-02-22T10:30:00Z"
}
```

Triggered Rules:
- If `temperature > maxTemp` → Log "TURN_FAN_ON"
- If `temperature < minTemp` → Log "TURN_HEATER_ON"
- If `humidity > maxHumidity` → Log "TURN_DEHUMIDIFIER_ON"
- If `humidity < minHumidity` → Log "TURN_HUMIDIFIER_ON"

#### Get All Automation Logs
```
GET /api/automation/logs
Authorization: Bearer <token>
```

#### Get Logs for Specific Zone
```
GET /api/automation/logs/{zoneId}
Authorization: Bearer <token>
```

### Crop Inventory Service APIs

#### Create Crop Batch
```
POST /api/crops
Content-Type: application/json
Authorization: Bearer <token>

{
  "name": "Tomato Batch 1",
  "zoneId": "1",
  "quantity": 500,
  "plantDate": "2026-02-01",
  "harvestDate": "2026-05-01"
}
```

#### Get Crop by ID
```
GET /api/crops/{id}
Authorization: Bearer <token>
```

#### Get All Crops
```
GET /api/crops
Authorization: Bearer <token>
```

#### Get Crops by Zone
```
GET /api/crops/zone/{zoneId}
Authorization: Bearer <token>
```

#### Update Crop Status
```
PUT /api/crops/{id}/status?status=VEGETATIVE
Authorization: Bearer <token>
```

Status values: `SEEDLING`, `VEGETATIVE`, `HARVESTED`

#### Delete Crop
```
DELETE /api/crops/{id}
Authorization: Bearer <token>
```

## API Testing with Postman

1. Import the provided Postman collection: `AGMS-API-Collection.json`
2. Set up environment variables:
   - `base_url`: http://localhost:8080
   - `token`: Your JWT token

3. Test the APIs following the collection structure

## Database

Each microservice uses an H2 in-memory database for demonstration purposes. 

### Access H2 Console

Each service exposes H2 console at:
- Zone Service: http://localhost:8081/h2-console
- Sensor Service: http://localhost:8082/h2-console
- Automation Service: http://localhost:8083/h2-console
- Crop Service: http://localhost:8084/h2-console

## Troubleshooting

### Services not registering with Eureka

1. Ensure Service Registry is running first
2. Check all services have correct `eureka.client.service-url.defaultZone=http://localhost:8761/eureka/`
3. Check logs for connection errors

### Config Server not loading configurations

1. Ensure config-repo directory exists at `d:/micro-service-architecture/config-repo`
2. Verify property files are in the correct directory
3. Check Config Server logs

### JWT Authentication Failures

1. Ensure valid Bearer token in Authorization header
2. Token should be in format: `Authorization: Bearer <token>`
3. Check gateway logs for validation errors

## Deployment Considerations

For production deployment:

1. **Database**: Replace H2 with PostgreSQL/MySQL
2. **Configuration**: Move to Git-based config repository
3. **Security**: Implement proper JWT token generation and refresh mechanism
4. **Monitoring**: Add Spring Boot Actuator endpoints
5. **Resilience**: Implement circuit breakers using Hystrix/Resilience4j
6. **Load Balancing**: Deploy multiple instances behind a load balancer

## Key Technologies

- **Spring Boot 4.0.3**: Microservices framework
- **Spring Cloud 2025.1.0**: Distributed systems framework
- **Spring Cloud Eureka**: Service discovery
- **Spring Cloud Gateway**: API Gateway
- **Spring Cloud Config**: Configuration management
- **Spring Cloud OpenFeign**: Declarative HTTP client
- **Spring Data JPA**: Data persistence
- **H2 Database**: In-memory database
- **JWT (jjwt)**: Token-based security
- **Lombok**: Reduce boilerplate code

## Git Commit History

This project follows a structured commit history showing:
- Infrastructure setup (Eureka, Gateway, Config)
- Service implementation (entities, repositories, services)
- API endpoints and integration
- Security and configuration

## Contributors

Automated Greenhouse Management System Implementation Team

## License

Educational Project
