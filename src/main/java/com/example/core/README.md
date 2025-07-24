# Core Components - Reusable Spring Boot Template

Thư mục `core` chứa các thành phần chung có thể tái sử dụng trong nhiều dự án Spring Boot khác. Các component này được thiết kế theo Clean Architecture và có thể được sao chép sang các dự án mới.

## 📁 Cấu trúc Core Components

### 1. Common Components (`core/common/`)

#### Exception Handling
- **`GlobalExceptionHandler`**: Handler chung cho tất cả exception
- **`ApiResponse<T>`**: Wrapper response chuẩn cho REST API
- **Exception classes**: `DomainException`, `EntityNotFoundException`, `BusinessRuleViolationException`

#### Utilities
- **`StringUtils`**: Các utility method cho string (validation, formatting, masking)
- **`DateTimeUtils`**: Các utility method cho date/time (formatting, conversion)
- **`PageUtils`**: Utilities cho pagination và sorting

#### Base Classes
- **`BaseMapper<E,D>`**: Interface chung cho entity-DTO mapping
- **`PageableRequest`**: Base request class cho paginated queries
- **`BaseDto`**: Base class cho tất cả DTOs

### 2. Application Layer (`core/application/`)

#### Use Case Components
- **Use case interfaces**: `UseCase`, `Command`, `Query`, `Handler`
- **DTO base classes**: `BaseDto`
- **Service dispatcher**: `UseCaseDispatcher`

#### Application Services (Clean Architecture Interfaces)
- **`CacheService`**: Interface cho caching operations
- **`ExternalApiService`**: Interface cho external API calls
- **`MessagingService`**: Interface cho messaging/event publishing
- **`SecurityService`**: Interface cho security operations

> 💡 **Lưu ý về Clean Architecture**: Các service interface này tuân thủ nguyên tắc Dependency Inversion. Application layer chỉ depend vào interface, không depend vào infrastructure implementation.

### 3. Domain Layer (`core/domain/`)
- **Base domain classes**: `Entity`, `AggregateRoot`, `ValueObject`
- **Repository interfaces**: Base repository patterns
- **Domain events**: `DomainEvent`, `DomainEventPublisher`

### 4. Infrastructure Layer (`core/infrastructure/`)

#### Configuration
- **`UseCaseConfig`**: Main configuration hub
- **`DatabaseConfig`**: JPA/Database configuration
- **`CacheConfig`**: Cache configuration
- **`SecurityConfig`**: Security configuration
- **`WebConfig`**: Web/CORS configuration
- **`LoggingConfig`**: Request/response logging
- **`ExternalApiConfig`**: External API resilience
- **`MessagingConfig`**: Event messaging configuration

#### Service Implementations
- **`CacheServiceImpl`**: Implementation of CacheService
- **`ExternalApiServiceImpl`**: Implementation of ExternalApiService
- **`MessagingServiceImpl`**: Implementation of MessagingService
- **`SecurityServiceImpl`**: Implementation of SecurityService

#### Adapters
- **Database adapters**: Generic repository implementations
- **Cache adapters**: Caching abstractions
- **External API adapters**: HTTP client với resilience patterns
- **Messaging adapters**: Event publishing/consuming

## 🚀 Cách sử dụng trong dự án mới

### Bước 1: Copy Core Package
```bash
# Copy toàn bộ thư mục core vào dự án mới
cp -r src/main/java/com/example/core new-project/src/main/java/com/yourcompany/core
```

### Bước 2: Update Package Names
Thay đổi package name từ `com.example.core` thành package phù hợp với dự án mới:
```bash
find new-project/src -name "*.java" -exec sed -i 's/com\.example\.core/com.yourcompany.core/g' {} +
```

### Bước 3: Add Dependencies
Đảm bảo `pom.xml` có các dependency cần thiết:
```xml
<dependencies>
    <!-- Spring Boot Starters -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    
    <!-- Resilience4j for external API -->
    <dependency>
        <groupId>io.github.resilience4j</groupId>
        <artifactId>resilience4j-spring-boot2</artifactId>
    </dependency>
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    
    <!-- MapStruct for mapping (if using BaseMapper) -->
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>1.5.5.Final</version>
    </dependency>
</dependencies>
```

### Bước 4: Main Application Configuration
```java
@SpringBootApplication
@Import(UseCaseConfig.class)  // Import tất cả core configurations
public class YourApplication {
    public static void main(String[] args) {
        SpringApplication.run(YourApplication.class, args);
    }
}
```

### Bước 5: Extend Core Components

#### Exception Handler
```java
@RestControllerAdvice
public class ProjectExceptionHandler extends GlobalExceptionHandler {
    // Add project-specific exception handling
}
```

#### Use Case Implementation  
```java
@Service
public class YourUseCaseHandler implements UseCaseHandler<YourCommand, YourResult> {
    
    // Inject application services (NOT infrastructure adapters)
    private final CacheService cacheService;
    private final ExternalApiService externalApiService;
    private final MessagingService messagingService;
    private final SecurityService securityService;
    
    // Your use case logic - follows Clean Architecture
}
```

#### Domain Entity
```java
@Entity
public class YourEntity extends AggregateRoot<Long> {
    // Your entity implementation
}
```

## 🔧 Clean Architecture Benefits

### 1. Dependency Inversion
- Application layer chỉ depend vào interface, không depend vào implementation
- Infrastructure layer implement các interface từ application layer
- Giúp code dễ test và thay đổi infrastructure mà không ảnh hưởng business logic

### 2. Separation of Concerns
- **Domain**: Business logic và rules
- **Application**: Use cases và orchestration
- **Infrastructure**: Technical details (database, cache, external APIs)

### 3. Testability
- Mock các service interface để test business logic
- Integration test cho infrastructure implementations
- Architecture test đảm bảo dependency rules

### 4. Reusability
- Core components có thể tái sử dụng trong nhiều project
- Consistent patterns và practices across projects

## 📝 Best Practices

1. **Tuân thủ Clean Architecture rules**:
   - Domain không depend vào Application hay Infrastructure
   - Application không depend vào Infrastructure
   - Sử dụng interface để invert dependencies

2. **Test thoroughly**: 
   - Unit test cho domain logic
   - Integration test cho use cases
   - Architecture test để enforce rules

3. **Configuration externalization**: 
   - Sử dụng `application.yml` cho configuration
   - Environment-specific configurations

4. **Error handling**: 
   - Extend `GlobalExceptionHandler` cho project-specific exceptions
   - Sử dụng `ApiResponse` cho consistent API responses

5. **Version control**: 
   - Maintain version của core components
   - Document breaking changes

## 🔄 Update Strategy

Khi có cải tiến trong core components:
1. Update core components trong template project này
2. Review changes và impact assessment
3. Gradually rollout changes to other projects
4. Maintain backward compatibility where possible

## 🏗️ Example Project Structure

```
your-project/
├── core/                           # ✅ Copied from template
│   ├── common/                     # Shared utilities & exceptions
│   ├── application/                # Use case interfaces & services
│   ├── domain/                     # Base domain classes
│   └── infrastructure/             # Configuration & implementations
├── business/                       # 🆕 Your business logic
│   ├── domain/                     # Your domain entities
│   ├── application/                # Your use case handlers
│   ├── infrastructure/             # Your repositories
│   └── presentation/               # Your controllers
└── config/                         # 🆕 Project-specific config
```

## 📖 Related Documentation

- [Architecture Overview](../docs/ARCHITECTURE.md)
- [Application Flow](../docs/APPLICATION_FLOW.md) 
- [Infrastructure Layers](../docs/INFRASTRUCTURE_LAYERS.md)
- [Quick Reference](../docs/QUICK_REFERENCE.md)

---

## ✅ Migration Summary

### Đã di chuyển thành công:

1. **`ApiResponse<T>`** → `core/common/response/`
2. **`GlobalExceptionHandler`** → `core/common/exception/handler/`
3. **Application Service Interfaces** → `core/application/service/`
4. **Infrastructure Service Implementations** → `core/infrastructure/service/`
5. **Common Utilities** → `core/common/util/`
6. **Base Classes** → `core/common/mapper/`, `core/common/request/`

### Tuân thủ Clean Architecture:
- ✅ Application layer chỉ depend vào interface
- ✅ Infrastructure layer implement interface
- ✅ Tất cả architecture test đã pass
- ✅ Separation of concerns rõ ràng
