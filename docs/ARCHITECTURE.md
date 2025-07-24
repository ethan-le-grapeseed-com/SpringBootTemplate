# Cấu trúc dự án Spring Boot Clean Architecture

## Tổng quan

Dự án này được thiết kế theo nguyên tắc Clean Architecture và Domain-Driven Design (DDD). Đây là một template có thể tái sử dụng cho các dự án Spring Boot, với core components được tách biệt để dễ dàng copy sang các project mới.

## 🎯 Nguyên tắc Clean Architecture

### Dependency Rule
```
Domain ← Application ← Infrastructure
```

- **Domain Layer**: Không depend vào layer nào khác
- **Application Layer**: Chỉ depend vào Domain Layer
- **Infrastructure Layer**: Implement interfaces từ Application Layer

### Lợi ích
- **Khả năng Kiểm thử**: Dễ mock và unit test
- **Tính Linh hoạt**: Dễ thay đổi infrastructure mà không ảnh hưởng business logic
- **Tái sử dụng**: Core components có thể tái sử dụng trong nhiều project

## Cấu trúc thư mục chi tiết

```
src/main/java/com/example/
│
├── core/                                    # 📦 CORE PACKAGE - Thành phần tái sử dụng
│   ├── common/                              # Shared components
│   │   ├── exception/                       # Exception handling
│   │   │   ├── handler/                     # Global exception handlers
│   │   │   │   └── GlobalExceptionHandler.java  # Common exception handler
│   │   │   ├── DomainException.java         # Base exception cho domain
│   │   │   ├── BusinessRuleViolationException.java
│   │   │   └── EntityNotFoundException.java
│   │   ├── response/                        # API Response wrappers
│   │   │   └── ApiResponse.java             # Generic response wrapper
│   │   ├── util/                           # Utility classes
│   │   │   ├── StringUtils.java            # String manipulation utilities
│   │   │   ├── DateTimeUtils.java          # Date/time utilities
│   │   │   └── PageUtils.java              # Pagination utilities
│   │   ├── mapper/                         # Mapping interfaces
│   │   │   └── BaseMapper.java             # Base mapper interface
│   │   ├── request/                        # Base request classes
│   │   │   └── PageableRequest.java        # Paginated request base
│   │   ├── event/                          # Domain events
│   │   │   ├── DomainEvent.java            # Interface cho domain events
│   │   │   ├── DomainEventHandler.java     # Handler cho events
│   │   │   └── DomainEventPublisher.java   # Publisher service
│   │   └── validation/                     # Validation utilities
│   │       ├── DomainValidator.java        # Domain validation
│   │       ├── ValidationError.java        # Validation error model
│   │       └── ValidationResult.java       # Validation result
│   │
│   ├── domain/                             # Domain layer (Business logic)
│   │   ├── model/                          # Domain models
│   │   │   ├── AggregateRoot.java          # Base class cho aggregates
│   │   │   ├── Entity.java                 # Base class cho entities
│   │   │   └── ValueObject.java            # Base class cho value objects
│   │   └── repository/                     # Repository interfaces
│   │       └── Repository.java             # Base repository interface
│   │
│   ├── application/                        # Application layer (Use cases & Services)
│   │   ├── usecase/                        # Use case interfaces
│   │   │   ├── UseCase.java                # Base use case interface
│   │   │   └── UseCaseHandler.java         # Use case handler
│   │   ├── command/                        # Commands (CQRS)
│   │   │   ├── Command.java                # Base command interface
│   │   │   └── CommandHandler.java         # Command handler interface
│   │   ├── query/                          # Queries (CQRS)
│   │   │   ├── Query.java                  # Base query interface
│   │   │   └── QueryHandler.java           # Query handler interface
│   │   ├── dto/                            # Data Transfer Objects
│   │   │   └── BaseDto.java                # Base DTO class
│   │   └── service/                        # Application service interfaces
│   │       ├── UseCaseDispatcher.java      # Use case dispatcher
│   │       ├── CacheService.java           # Cache operations interface
│   │       ├── ExternalApiService.java     # External API interface
│   │       ├── MessagingService.java       # Messaging interface
│   │       └── SecurityService.java        # Security interface
│   │
│   └── infrastructure/                     # Infrastructure layer
│       ├── service/                         # Application service implementations
│       │   ├── CacheServiceImpl.java        # Cache service implementation
│       │   ├── ExternalApiServiceImpl.java  # External API service implementation
│       │   ├── MessagingServiceImpl.java    # Messaging service implementation
│       │   └── SecurityServiceImpl.java     # Security service implementation
│       ├── database/                        # Database layer
│       │   ├── config/                      # Database configuration
│       │   │   └── DatabaseConfig.java
│       │   └── adapter/                     # Database adapters
│       │       └── DatabaseRepositoryAdapter.java
│       ├── cache/                           # Cache layer
│       │   ├── config/                      # Cache configuration
│       │   │   └── CacheConfig.java
│       │   └── adapter/                     # Cache adapters
│       │       └── CacheAdapter.java
│       ├── external/                        # External API layer
│       │   ├── config/                      # External API configuration
│       │   │   └── ExternalApiConfig.java
│       │   ├── adapter/                     # External API adapters
│       │   │   └── ExternalApiAdapter.java
│       │   └── exception/                   # External API exceptions
│       │       └── ExternalApiException.java
│       ├── messaging/                       # Messaging layer
│       │   ├── config/                      # Messaging configuration
│       │   │   └── MessagingConfig.java
│       │   ├── adapter/                     # Messaging adapters
│       │   │   └── MessagingAdapter.java
│       │   └── publisher/                   # Event publishers
│       │       └── SpringDomainEventPublisher.java
│       ├── security/                        # Security layer
│       │   ├── config/                      # Security configuration
│       │   │   └── SecurityConfig.java
│       │   └── adapter/                     # Security adapters
│       │       └── SecurityAdapter.java
│       ├── service/                         # Infrastructure services
│       │   └── SpringUseCaseDispatcher.java
│       └── config/                          # Configuration hub
│           ├── UseCaseConfig.java           # Main configuration
│           ├── WebConfig.java               # Web & CORS configuration
│           └── LoggingConfig.java           # Request/response logging
│
└── demo/                                    # 📦 DEMO PACKAGE - Ví dụ implementation
    ├── domain/                              # Demo domain logic
    │   ├── model/                           # Domain models
    │   │   ├── User.java                    # User aggregate root
    │   │   ├── Email.java                   # Email value object
    │   │   └── EmailEmbeddable.java         # JPA embeddable for Email
    │   ├── event/                           # Domain events
    │   │   ├── UserCreatedEvent.java
    │   │   └── UserEmailChangedEvent.java
    │   └── repository/                      # Repository interfaces
    │       └── UserRepository.java
    │
    ├── application/                         # Demo application services
    │   ├── command/                         # Commands
    │   │   ├── CreateUserCommand.java
    │   │   └── CreateUserCommandHandler.java
    │   ├── query/                           # Queries
    │   │   ├── GetUserByIdQuery.java
    │   │   └── GetUserByIdQueryHandler.java
    │   ├── dto/                             # DTOs
    │   │   └── UserDto.java
    │   ├── mapper/                          # Object mapping
    │   │   └── UserMapper.java              # MapStruct mapper
    │   ├── service/                         # Application services
    │   │   └── UserIntegrationService.java  # Demo integration service
    │   └── eventhandler/                    # Event handlers
    │       └── UserCreatedEventHandler.java
    │
    ├── infrastructure/                      # Demo infrastructure
    │   ├── database/                        # Database implementation
    │   │   ├── repository/                  # JPA repositories
    │   │   │   └── UserJpaRepository.java
    │   │   └── entity/                      # JPA entities
    │   │       └── UserEntity.java
    │   └── adapter/                         # Infrastructure adapters
    │       ├── CacheAdapter.java
    │       ├── ExternalApiAdapter.java
    │       ├── MessagingAdapter.java
    │       └── SecurityAdapter.java
    │
    └── presentation/                        # Demo web layer
        ├── controller/                      # REST controllers
        │   └── UserController.java
        ├── request/                         # Request DTOs
        │   ├── CreateUserRequest.java
        │   └── UserRequest.java
        ├── response/                        # Response DTOs (inherits from core)
        │   └── UserResponse.java
        └── exception/                       # Controller exception handling
            └── UserControllerAdvice.java    # Extends GlobalExceptionHandler
```

## 🏗️ Clean Architecture Layers

### 🎯 **Nguyên tắc Cốt lõi**
- **Dependency Rule**: Dependencies hướng vào trong (về phía domain)
- **Interface Segregation**: Interfaces mỏng, tập trung
- **Dependency Inversion**: Application phụ thuộc vào abstractions, không phải implementations
- **Single Responsibility**: Mỗi component có một lý do để thay đổi

### 🔧 **Trách nhiệm của từng Layer**

#### 1. **Domain Layer** (`core/domain/`)
**Pure business logic - không dependencies bên ngoài**
```java
// ✅ Được phép
class User {
    private Email email;
    
    public void changeEmail(Email newEmail) {
        // Business rules ở đây
        if (!isValidEmail(newEmail)) {
            throw new BusinessRuleViolationException("Invalid email");
        }
        this.email = newEmail;
        // Raise domain event
        DomainEventPublisher.raise(new UserEmailChangedEvent(this.id, newEmail));
    }
}

// ❌ Không được phép - external dependencies
class User {
    @Autowired
    private EmailService emailService; // ❌ Không có Spring annotations trong domain
}
```

#### 2. **Application Layer** (`core/application/`)
**Điều phối các business operations - định nghĩa interfaces cho infrastructure**
```java
// ✅ Application Service Interface
public interface CacheService {
    void put(String key, Object value);
    Optional<Object> get(String key);
    void evict(String key);
}

// ✅ Command Handler
@Component
public class CreateUserCommandHandler {
    private final UserRepository userRepository;
    private final CacheService cacheService; // Interface, không phải implementation
    
    public UserDto handle(CreateUserCommand command) {
        // Điều phối use case
        User user = User.create(command.getName(), command.getEmail());
        userRepository.save(user);
        cacheService.put("user:" + user.getId(), user);
        return UserMapper.toDto(user);
    }
}
```

#### 3. **Infrastructure Layer** (`core/infrastructure/`)
**Triển khai kỹ thuật - implements application interfaces**
```java
// ✅ Service Implementation
@Service
public class CacheServiceImpl implements CacheService {
    private final CacheManager cacheManager;
    
    @Override
    public void put(String key, Object value) {
        cacheManager.getCache("default").put(key, value);
    }
    
    @Override
    public Optional<Object> get(String key) {
        Cache.ValueWrapper wrapper = cacheManager.getCache("default").get(key);
        return Optional.ofNullable(wrapper != null ? wrapper.get() : null);
    }
}

// ✅ Configuration
@Configuration
public class UseCaseConfig {
    @Bean
    public CacheService cacheService(CacheManager cacheManager) {
        return new CacheServiceImpl(cacheManager);
    }
}
```

### 🔄 **Dependency Flow**
```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│Presentation │───▶│Application  │───▶│   Domain    │
└─────────────┘    └─────────────┘    └─────────────┘
       │                   ▲                   │
       │                   │                   │
       ▼                   │                   │
┌─────────────┐            │                   │
│Infrastructure│───────────┘                   │
│(implements  │◄──────────────────────────────┘
│ interfaces) │
└─────────────┘

Luồng Dependencies:
• Presentation ──▶ Application ──▶ Domain
• Infrastructure ──▶ Application (implements interfaces)
• Infrastructure ──▶ Domain (implements repositories)
```

**Quy tắc chính:**
- ✅ Presentation có thể depend vào Application
- ✅ Application có thể depend vào Domain  
- ✅ Infrastructure implements Application interfaces
- ❌ Domain không thể depend vào bất cứ thứ gì
- ❌ Application không thể depend trực tiếp vào Infrastructure

## 🎨 **Các Design Patterns Được Sử dụng**

### **1. Repository Pattern**
```java
// Domain interface
public interface UserRepository {
    User save(User user);
    Optional<User> findById(UserId id);
    List<User> findAll();
}

// Infrastructure implementation
@Repository
public class JpaUserRepository implements UserRepository {
    // JPA implementation details
}
```

### **2. CQRS (Command Query Responsibility Segregation)**
```java
// Commands - thay đổi state
public class CreateUserCommand {
    private final String name;
    private final String email;
}

// Queries - đọc state
public class GetUserByIdQuery {
    private final Long userId;
}
```

### **3. Service Interface Pattern**
```java
// Application interface (sạch)
public interface MessagingService {
    void sendMessage(String topic, Object message);
    void subscribe(String topic, MessageHandler handler);
}

// Infrastructure implementation (kỹ thuật)
@Service
public class MessagingServiceImpl implements MessagingService {
    // Kafka, RabbitMQ, etc. implementation
}
```

### **4. Factory & Builder Patterns**
```java
// Domain factories
public class UserFactory {
    public static User create(String name, Email email) {
        return User.builder()
            .id(UserId.generate())
            .name(name)
            .email(email)
            .createdAt(Instant.now())
            .build();
    }
}
```

## 🧪 **Chiến lược Testing**

### **Architecture Testing**
```java
@Test
void applicationShouldNotDependOnInfrastructure() {
    ArchRule rule = noClasses()
        .that().resideInAPackage("..application..")
        .should().dependOnClassesThat()
        .resideInAPackage("..infrastructure..");
    
    rule.check(JavaClasses.of("com.example"));
}
```

### **Layer Testing**
- **Domain**: Pure unit tests, không có Spring context
- **Application**: Integration tests với mocked infrastructure
- **Infrastructure**: Integration tests với real databases/services
- **Presentation**: Web layer tests với MockMvc

## 🚀 **Hướng dẫn Bắt đầu Nhanh**

### **1. Chạy Ứng dụng**
```bash
mvn spring-boot:run
```

### **2. Truy cập API Documentation**
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:testdb`)

### **3. Kiểm thử APIs**
```bash
# Tạo user mới
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","email":"john@example.com"}'

# Lấy user theo ID
curl http://localhost:8080/api/users/1

# Response format (sử dụng core ApiResponse)
{
  "success": true,
  "data": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com"
  },
  "message": "User retrieved successfully",
  "timestamp": "2024-01-15T10:30:00Z"
}
```

### **4. Chạy Tests**
```bash
# Chạy tất cả tests
mvn test

# Chạy architecture tests cụ thể
mvn test -Dtest=ArchitectureTest

# Chạy với coverage
mvn test jacoco:report
```

## 📋 **Hướng dẫn Phát triển**

### **Thêm Tính năng Mới**
1. **Bắt đầu với Domain**: Định nghĩa business entities và rules
2. **Tạo Application Services**: Định nghĩa use case interfaces
3. **Implement Infrastructure**: Tạo technical implementations
4. **Thêm Presentation Layer**: Controllers và DTOs
5. **Viết Tests**: Theo testing pyramid (unit → integration → e2e)

### **Common Utilities Có sẵn**
```java
// String utilities
boolean isEmpty = StringUtils.isBlank(string);
String capitalized = StringUtils.capitalize(string);
String truncated = StringUtils.truncate(string, maxLength);

// Date/Time utilities  
Instant now = DateTimeUtils.now();
String formatted = DateTimeUtils.format(instant, pattern);
LocalDate date = DateTimeUtils.parseDate(dateString);

// Pagination utilities
Pageable pageable = PageUtils.createPageable(page, size, sort);
PageableResponse<T> response = PageUtils.createResponse(content, pageable, totalElements);

// Base mapping (MapStruct integration)
@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDto> {
    // MapStruct will implement common methods automatically
    // toDto(), toEntity(), toEntityList(), toDtoList()
}
```

### **Error Handling Best Practices**
```java
// Sử dụng domain-specific exceptions
throw new BusinessRuleViolationException("Email already exists");
throw new EntityNotFoundException("User not found with ID: " + id);

// GlobalExceptionHandler sẽ tự động format responses
{
  "success": false,
  "error": {
    "code": "BUSINESS_RULE_VIOLATION", 
    "message": "Email already exists"
  },
  "timestamp": "2024-01-15T10:30:00Z"
}
```

## 🎯 **Lợi ích của Template này**

### **Cho Đội Ngũ Phát triển**
- ✅ **Cấu trúc Nhất quán**: Tất cả projects đều theo cùng một pattern
- ✅ **Giảm Boilerplate**: Common utilities và patterns sẵn sàng sử dụng
- ✅ **Testing Tốt hơn**: Architecture hỗ trợ tất cả testing levels
- ✅ **Onboarding Dễ dàng**: Developers mới có thể hiểu cấu trúc nhanh chóng

### **Cho Business**
- ✅ **Phát triển Nhanh hơn**: Reusable components và patterns
- ✅ **Bảo trì Thấp hơn**: Clean code dễ maintain hơn
- ✅ **Giảm Rủi ro**: Architecture đã được kiểm thử kỹ lưỡng
- ✅ **Khả năng Mở rộng**: Có thể phát triển cùng với nhu cầu business

### **Ưu điểm Kỹ thuật**
- ✅ **Dependency Inversion**: Dễ mock và test
- ✅ **Loose Coupling**: Thay đổi trong một layer không ảnh hưởng các layer khác
- ✅ **High Cohesion**: Code liên quan được nhóm lại với nhau
- ✅ **SOLID Principles**: Làm cho code robust và extensible
