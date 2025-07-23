# Cấu trúc dự án Spring Boot Clean Architecture

## Tổng quan

Dự án này được thiết kế theo nguyên tắc Clean Architecture và Domain-Driven Design (DDD).

## Cấu trúc thư mục chi tiết

```
src/main/java/com/example/
│
├── core/                                    # 📦 CORE PACKAGE - Thành phần tái sử dụng
│   ├── common/                              # Tiện ích chung
│   │   ├── exception/                       # Exception classes
│   │   │   ├── DomainException.java         # Base exception cho domain
│   │   │   ├── BusinessRuleViolationException.java
│   │   │   └── EntityNotFoundException.java
│   │   ├── event/                           # Domain events
│   │   │   ├── DomainEvent.java             # Interface cho domain events
│   │   │   ├── DomainEventHandler.java      # Handler cho events
│   │   │   └── DomainEventPublisher.java    # Publisher service
│   │   └── validation/                      # Validation utilities
│   │       ├── DomainValidator.java         # Domain validation
│   │       ├── ValidationError.java         # Validation error model
│   │       └── ValidationResult.java        # Validation result
│   │
│   ├── domain/                              # Domain layer (Business logic)
│   │   ├── model/                           # Domain models
│   │   │   ├── AggregateRoot.java           # Base class cho aggregates
│   │   │   ├── Entity.java                  # Base class cho entities
│   │   │   └── ValueObject.java             # Base class cho value objects
│   │   └── repository/                      # Repository interfaces
│   │       └── Repository.java              # Base repository interface
│   │
│   ├── application/                         # Application layer (Use cases)
│   │   ├── usecase/                         # Use case interfaces
│   │   │   ├── UseCase.java                 # Base use case interface
│   │   │   └── UseCaseHandler.java          # Use case handler
│   │   ├── command/                         # Commands (CQRS)
│   │   │   ├── Command.java                 # Base command interface
│   │   │   └── CommandHandler.java          # Command handler interface
│   │   ├── query/                           # Queries (CQRS)
│   │   │   ├── Query.java                   # Base query interface
│   │   │   └── QueryHandler.java            # Query handler interface
│   │   ├── dto/                             # Data Transfer Objects
│   │   │   └── BaseDto.java                 # Base DTO class
│   │   └── service/                         # Application services
│   │       └── UseCaseDispatcher.java       # Use case dispatcher
│   │
│   └── infrastructure/                      # Infrastructure layer
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
│       └── config/                          # Main configuration
│           └── UseCaseConfig.java
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
    │   └── eventhandler/                    # Event handlers
    │       └── UserCreatedEventHandler.java
    │
    ├── infrastructure/                      # Demo infrastructure
    │   ├── database/                        # Database implementation
    │   │   ├── repository/                  # JPA repositories
    │   │   │   └── JpaUserRepository.java
    │   │   └── adapter/                     # Database adapters
    │   │       └── UserDatabaseAdapter.java
    │   └── config/                          # Configuration
    │       └── JpaConfig.java
    │
    └── presentation/                        # Demo REST API
        ├── controller/                      # Controllers
        │   └── UserController.java
        ├── request/                         # Request models
        │   └── CreateUserRequest.java
        ├── response/                        # Response models
        │   ├── ApiResponse.java
        │   └── CreateUserResponse.java
        └── exception/                       # Exception handling
            └── GlobalExceptionHandler.java
```

## Nguyên tắc Clean Architecture được áp dụng

### 1. **Dependency Rule** (Nguyên tắc phụ thuộc)
- Dependencies chỉ hướng vào trong
- Domain không phụ thuộc vào layer nào khác
- Infrastructure phụ thuộc vào Domain interfaces

### 2. **Separation of Concerns** (Tách biệt trách nhiệm)
- **Domain**: Business logic, business rules
- **Application**: Use cases, application services
- **Infrastructure**: Database, external services
- **Presentation**: Controllers, request/response models

### 3. **SOLID Principles**
- **Single Responsibility**: Mỗi class có một trách nhiệm duy nhất
- **Open/Closed**: Mở cho mở rộng, đóng cho sửa đổi
- **Liskov Substitution**: Có thể thay thế implementations
- **Interface Segregation**: Interfaces nhỏ và chuyên biệt
- **Dependency Inversion**: Phụ thuộc vào abstractions

## Các pattern được sử dụng

1. **Repository Pattern**: Tách biệt data access logic
2. **CQRS**: Tách biệt Command và Query
3. **Domain Events**: Event-driven architecture
4. **Value Objects**: Immutable objects với business meaning
5. **Aggregate Pattern**: Đảm bảo tính nhất quán của domain
6. **Factory Pattern**: Tạo objects phức tạp
7. **Adapter Pattern**: Kết nối với external systems

## Hướng dẫn sử dụng

### 1. **Khởi chạy ứng dụng**
```bash
mvn spring-boot:run
```

### 2. **Truy cập API Documentation**
- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console

### 3. **Test API**
```bash
# Tạo user mới
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","email":"john@example.com"}'

# Lấy user theo ID
curl http://localhost:8080/api/users/1
```

### 4. **Chạy tests**
```bash
mvn test
```

## Best Practices

1. **Domain Models**: Luôn validate business rules trong domain
2. **Value Objects**: Sử dụng cho concepts có meaning nhưng không có identity
3. **Events**: Sử dụng domain events cho cross-aggregate communication
4. **Testing**: Unit test domain logic, integration test infrastructure
5. **Error Handling**: Sử dụng specific exceptions cho business rules
6. **Mapping**: Tách biệt domain models và DTOs

## Lợi ích của template

1. **Familiar Patterns**: Các pattern được sử dụng rộng rãi
2. **Clean Code**: Code dễ đọc, dễ maintain
3. **Testable**: Architecture hỗ trợ testing tốt
4. **Scalable**: Dễ mở rộng và thêm features mới
5. **Industry Standard**: Tuân theo các best practices của industry
