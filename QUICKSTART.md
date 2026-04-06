# Quick Start Guide - AGMS

## Prerequisites
- Java 17+ installed
- Maven 3.9+ installed
- Git

## 60-Second Startup

### Terminal 1: Service Registry
```bash
cd service-registery
mvn clean spring-boot:run
# Wait for: "Started ServiceRegisteryApplication"
```

### Terminal 2: Config Server
```bash
cd Config-Server
mvn clean spring-boot:run
# Wait for: "Started ConfigServerApplication"
```

### Terminal 3: API Gateway
```bash
cd API-Gateway
mvn clean spring-boot:run
# Wait for: "Started ApiGatewayApplication"
```

### Terminal 4-7: Domain Services (Can start in any order)

**Terminal 4:**
```bash
cd Zone-Management-Service
mvn clean spring-boot:run
```

**Terminal 5:**
```bash
cd Sensor-Telemetry-Service
mvn clean spring-boot:run
```

**Terminal 6:**
```bash
cd Automation-Control-Service
mvn clean spring-boot:run
```

**Terminal 7:**
```bash
cd Crop-Inventory-Service
mvn clean spring-boot:run
```

## Verify Everything is Running

### Check Eureka Dashboard
Open browser: `http://localhost:8761`

You should see **7 services registered**:
- ✅ service-registry
- ✅ api-gateway
- ✅ config-server
- ✅ zone-management-service
- ✅ sensor-telemetry-service
- ✅ automation-control-service
- ✅ crop-inventory-service

All should show **Status: UP**

## Test a Simple API

### Using CURL
```bash
# Create a zone
curl -X POST http://localhost:8080/api/zones \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer test-token" \
  -d '{
    "name": "Test Zone",
    "description": "Testing zone",
    "minTemp": 20.0,
    "maxTemp": 28.0,
    "minHumidity": 60.0,
    "maxHumidity": 80.0
  }'

# Get all zones
curl -X GET http://localhost:8080/api/zones \
  -H "Authorization: Bearer test-token"
```

### Using Postman
1. Import: `AGMS-API-Collection.json`
2. Set variables:
   - `base_url`: http://localhost:8080
   - `token`: test-token (or any string)
3. Start with "Create Zone" request
4. Copy the zone ID from response
5. Use it in "Get Zone by ID" request

## Common Issues & Fixes

### Services not appearing in Eureka
**Problem**: Service registered as UNKNOWN
**Fix**: Wait 30 seconds, refresh Eureka dashboard

### 401 Unauthorized errors
**Problem**: No Authorization header
**Fix**: Add header: `Authorization: Bearer <any-token>`

### Port already in use
**Problem**: Address already in use (port XXXX)
**Fix**: Kill existing process or use different port in application.properties

### Config Server connection timeout
**Problem**: Connection timeout to localhost:8888
**Fix**: Ensure Config-Server is running before starting other services

## File Locations

```
d:\micro-service-architecture\
├── service-registery/              # Port 8761
├── API-Gateway/                    # Port 8080
├── Config-Server/                  # Port 8888
├── Zone-Management-Service/        # Port 8081
├── Sensor-Telemetry-Service/       # Port 8082
├── Automation-Control-Service/     # Port 8083
├── Crop-Inventory-Service/         # Port 8084
├── README.md                       # Full documentation
├── AGMS-API-Collection.json        # Postman collection
└── COMPLETION_SUMMARY.md           # This summary
```

## Next Steps

1. ✅ Read [README.md](./README.md) for complete API documentation
2. ✅ Import [AGMS-API-Collection.json](./AGMS-API-Collection.json) into Postman
3. ✅ Review [docs/IMPLEMENTATION_GUIDE.md](./docs/IMPLEMENTATION_GUIDE.md) for technical details
4. ✅ Follow [docs/COMMIT_STRATEGY.md](./docs/COMMIT_STRATEGY.md) for Git practices

## Quick API Reference

| Service | Port | Base URL | Example |
|---------|------|----------|---------|
| API Gateway | 8080 | http://localhost:8080 | /api/zones |
| Zone Service | 8081 | http://localhost:8081 | /api/zones |
| Sensor Service | 8082 | http://localhost:8082 | /api/sensors/latest/{deviceId} |
| Automation Service | 8083 | http://localhost:8083 | /api/automation/logs |
| Crop Service | 8084 | http://localhost:8084 | /api/crops |
| Eureka | 8761 | http://localhost:8761 | Dashboard |
| Config Server | 8888 | http://localhost:8888 | /config/{service}/default |

## Service Endpoints

### Zone Management (Create, Read, Update, Delete)
- `POST /api/zones` - Create zone
- `GET /api/zones` - List zones
- `GET /api/zones/{id}` - Get zone
- `PUT /api/zones/{id}` - Update zone
- `DELETE /api/zones/{id}` - Delete zone

### Sensor Telemetry
- `GET /api/sensors/latest/{deviceId}` - Latest reading

### Automation Control
- `GET /api/automation/logs` - All logs
- `GET /api/automation/logs/{zoneId}` - Zone logs
- `POST /api/automation/process` - Internal processing

### Crop Inventory
- `POST /api/crops` - Create crop
- `GET /api/crops` - List crops
- `GET /api/crops/{id}` - Get crop
- `GET /api/crops/zone/{zoneId}` - Zone crops
- `PUT /api/crops/{id}/status` - Update status
- `DELETE /api/crops/{id}` - Delete crop

## FAQ

**Q: What's the default JWT token?**
A: For testing, any string works (e.g., "test-token")

**Q: Can I start services in different order?**
A: Service Registry (8761) MUST start first. Others can be in any order.

**Q: How do I reset the database?**
A: Each service uses H2 in-memory. Restart the service.

**Q: Where are the databases?**
A: In-memory H2. Access via `/h2-console` on each service port.

**Q: How do I change a port?**
A: Edit `application.properties` in the service folder.

---

**Ready to develop?** Read the complete [README.md](./README.md)!
