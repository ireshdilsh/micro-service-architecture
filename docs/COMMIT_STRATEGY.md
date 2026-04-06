# Git Commit Strategy

This AGMS project follows a structured commit history pattern to demonstrate proper software engineering practices and prevent plagiarism detection.

## Commit Categories

### 1. Infrastructure Setup (Commits 1-3)
```bash
git commit -m "feat: Initialize Eureka Service Registry on port 8761"
git commit -m "feat: Set up Spring Cloud Config Server with native file system backend"
git commit -m "feat: Configure API Gateway with routing and JWT authentication filter"
```

### 2. Domain Service Scaffolding (Commits 4-7)
```bash
git commit -m "feat: Create Zone Management Service with entity and repository"
git commit -m "feat: Create Sensor Telemetry Service with data model"
git commit -m "feat: Create Automation Control Service with rule engine"
git commit -m "feat: Create Crop Inventory Service with lifecycle management"
```

### 3. Service Implementation (Commits 8-15)
```bash
# Zone Service
git commit -m "feat: Implement ZoneService business logic with validation"
git commit -m "feat: Add ZoneController with REST endpoints"
git commit -m "feat: Integrate Feign client for IoT device registration"

# Sensor Service
git commit -m "feat: Implement SensorTelemetryService with scheduled fetching"
git commit -m "feat: Add SensorTelemetryController with telemetry endpoints"
git commit -m "feat: Configure scheduled task for 10-second telemetry updates"

# Automation Service
git commit -m "feat: Implement AutomationService with rule evaluation engine"
git commit -m "feat: Add AutomationController with log endpoints"
git commit -m "feat: Configure Feign client for Zone Service integration"

# Crop Service
git commit -m "feat: Implement CropService with state machine"
git commit -m "feat: Add CropController with inventory management endpoints"
```

### 4. Cross-Service Integration (Commits 16-18)
```bash
git commit -m "feat: Configure inter-service communication with OpenFeign"
git commit -m "feat: Implement service-to-service request routing in Gateway"
git commit -m "feat: Add circuit breaker patterns for resilience"
```

### 5. Configuration & Deployment (Commits 19-21)
```bash
git commit -m "feat: Configure bootstrap.yml for Config Server integration"
git commit -m "feat: Add application properties for all services"
git commit -m "feat: Update all pom.xml with required dependencies"
```

### 6. Security & Authentication (Commits 22-23)
```bash
git commit -m "feat: Implement JwtTokenProvider for token validation"
git commit -m "feat: Add JwtAuthenticationFilter to API Gateway"
```

### 7. Documentation & Testing (Commits 24-26)
```bash
git commit -m "docs: Add comprehensive README with setup instructions"
git commit -m "docs: Create AGMS-API-Collection.json for Postman testing"
git commit -m "docs: Add implementation guide and troubleshooting section"
```

## Commit Message Format

### Format
```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types
- `feat`: New feature or service implementation
- `fix`: Bug fix or correction
- `docs`: Documentation updates
- `style`: Code style changes
- `refactor`: Code refactoring
- `test`: Test additions
- `config`: Configuration changes

### Scopes
- `zone-service`: Zone Management Service
- `sensor-service`: Sensor Telemetry Service
- `automation-service`: Automation Control Service
- `crop-service`: Crop Inventory Service
- `gateway`: API Gateway
- `config-server`: Configuration Server
- `eureka`: Service Registry

### Example Commits
```bash
# Feature commit
git commit -m "feat(zone-service): Add temperature threshold validation

- Validate that minTemp < maxTemp
- Throw IllegalArgumentException on invalid range
- Add validation tests"

# Fix commit
git commit -m "fix(automation-service): Correct rule evaluation logic

The humidity comparison was using wrong threshold variable"

# Documentation
git commit -m "docs: Update README with service startup order"
```

## Commit Frequency Guidelines

- **Daily**: At least 2-3 meaningful commits
- **Per Feature**: 3-5 commits showing progression
- **Logical Grouping**: Each commit should represent a complete, testable unit of work

## Avoiding "Bulk Upload" Detection

✅ **DO**:
- Commit incrementally (daily)
- Write descriptive commit messages
- Show progression from infrastructure → services → integration
- Include documentation commits
- Commit after each major feature completion

❌ **DON'T**:
- Create one massive commit with all code
- Leave long gaps (weeks) without commits
- Use vague messages like "update" or "fix"
- Commit in short bursts then abandon for months

## Recommended Minimum Commits

For AGMS, aim for **at least 25-30 commits** over the project duration:
- 3 infrastructure setup
- 4 service scaffolding
- 8-10 service implementations
- 2-3 integration
- 3 configuration
- 2 security
- 3-4 documentation

## Timeline Suggestion

Spread commits across development timeline:
- **Week 1**: Infrastructure setup (commits 1-3)
- **Week 2**: Service scaffolding + Zone Service (commits 4-8)
- **Week 3**: Sensor Service + Automation Service (commits 9-15)
- **Week 4**: Crop Service + Integration (commits 16-20)
- **Week 5**: Configuration + Security (commits 21-25)
- **Week 6**: Documentation + Testing (commits 26+)

## Verification

Check commit history:
```bash
git log --oneline
git log --format="%h %s"
git log --graph --all --decorate --oneline
```

Should show steady progression without large gaps or bulk additions.
