# 🚀 Hướng dẫn Nhanh

## 📁 Cấu trúc Dự án

```
src/main/java/com/example/
├── core/                           # 🔧 THÀNH PHẦN CORE TÁI SỬ DỤNG
│   ├── common/                     # Common utilities
│   │   ├── response/               # ApiResponse<T>
│   │   ├── exception/              # Business exceptions + GlobalExceptionHandler
│   │   ├── validation/             # Validation utilities
│   │   ├── event/                  # Domain event infrastructure
│   │   └── utils/                  # StringUtils, DateTimeUtils, PageUtils
│   ├── application/                # Application interfaces (Clean Architecture)
│   │   ├── service/                # CacheService, ExternalApiService, etc.
│   │   ├── command/                # Base command interfaces
│   │   ├── query/                  # Base query interfaces
│   │   ├── dto/                    # Base DTOs and PageableRequest
│   │   └── usecase/                # Use case interfaces
│   ├── domain/                     # Domain base classes
│   │   ├── model/                  # BaseEntity, AggregateRoot, ValueObject
│   │   └── repository/             # Repository interfaces
│   └── infrastructure/             # Infrastructure implementations
│       ├── service/                # CacheServiceImpl, ExternalApiServiceImpl, etc.
│       ├── config/                 # UseCaseConfig, WebConfig, LoggingConfig
│       └── */adapter/              # Technical adapters for each layer
└── demo/                           # 📦 DEMO TRIỂN KHAI MẪU
    ├── domain/                     # User aggregate, Email value object
    ├── application/                # Command/Query handlers, DTOs, Mappers
    ├── infrastructure/             # JPA repositories, database entities
    └── presentation/               # REST controllers, Request/Response DTOs
```

## 🌐 API Endpoints

| Phương thức | Endpoint | Mô tả | Định dạng Response |
|--------|----------|-------|-----------------|
| `GET` | `/api/users` | Lấy tất cả users | `ApiResponse<List<UserResponse>>` |
| `GET` | `/api/users/{id}` | Lấy user theo ID | `ApiResponse<UserResponse>` |
| `POST` | `/api/users` | Tạo user mới | `ApiResponse<CreateUserResponse>` |

### 📝 Ví dụ Request/Response

**Tạo User:**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","email":"john.doe@example.com"}'
```

**Response Thành công:**
```json
{
  "success": true,
  "data": {
    "userId": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com"
  },
  "message": "User created successfully",
  "timestamp": "2024-01-15T10:30:00Z"
}
```

**Response Lỗi:**
```json
{
  "success": false,
  "error": {
    "code": "BUSINESS_RULE_VIOLATION",
    "message": "User with email john.doe@example.com already exists"
  },
  "timestamp": "2024-01-15T10:30:00Z"
}
```

## 🧩 Core Components

### 1. **ApiResponse<T>** - Standardized Response Format
```java
// Response thành công
ApiResponse<UserDto> response = ApiResponse.success(userDto, "User created");

// Response lỗi  
ApiResponse<Void> error = ApiResponse.error("VALIDATION_ERROR", "Invalid email");

// Sử dụng trong controllers
@PostMapping
public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody CreateUserRequest request) {
    UserDto user = userService.createUser(request);
    UserResponse response = UserMapper.toResponse(user);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(response, "User created successfully"));
}
```

### 2. **Application Service Interfaces** - Clean Architecture
```java
// Define contracts in application layer
public interface CacheService {
    void put(String key, Object value);
    Optional<Object> get(String key);
    void evict(String key);
}

public interface ExternalApiService {
    <T> T get(String url, Class<T> responseType);
    <T> T post(String url, Object request, Class<T> responseType);
}

// Use in application services
@Service
public class UserIntegrationService {
    private final CacheService cacheService;
    private final ExternalApiService externalApiService;
    
    public UserDto processUser(CreateUserCommand command) {
        // Use interface, not implementation
        cacheService.put("processing:" + command.getEmail(), command);
        
        ValidationResponse validation = externalApiService.post(
            "/api/validate", command, ValidationResponse.class);
        
        // Business logic...
    }
}
```

### 3. **Domain Layer** - Business Logic
```java
// User Aggregate Root
@Entity
public class User extends AggregateRoot<Long> {
    // Factory method with business rules
    public static User create(String firstName, String lastName, Email email) {
        User user = new User(firstName, lastName, email);
        user.addDomainEvent(new UserCreatedEvent(user.getId(), user.getEmail()));
        return user;
    }
    
    // Business methods
    public void changeEmail(Email newEmail) {
        if (!this.email.equals(newEmail)) {
            this.email = newEmail;
            this.addDomainEvent(new UserEmailChangedEvent(this.id, newEmail));
        }
    }
}

// Email Value Object with validation
@Embeddable
public class Email extends ValueObject {
    public static Email of(String value) {
        if (StringUtils.isBlank(value)) {
            throw new BusinessRuleViolationException("Email cannot be blank");
        }
        return new Email(value.trim().toLowerCase());
    }
}
```

## 🔧 Utility Classes

### **StringUtils** - String Operations
```java
// Check if string is blank
StringUtils.isBlank("  ") // true
StringUtils.isNotBlank("Hello") // true

// Capitalize string
StringUtils.capitalize("john doe") // "John doe"

// Truncate with ellipsis
StringUtils.truncate("Very long text", 10) // "Very lo..."
```

### **DateTimeUtils** - Date/Time Operations  
```java
// Current timestamp
Instant now = DateTimeUtils.now()

// Format timestamp
String formatted = DateTimeUtils.format(instant, "yyyy-MM-dd HH:mm:ss")

// Parse date
LocalDate date = DateTimeUtils.parseDate("2024-01-15")
```

### **PageUtils** - Pagination Utilities
```java
// Create pageable request
Pageable pageable = PageUtils.createPageable(0, 10, "name");

// Create page response
PageableResponse<UserDto> response = PageUtils.createResponse(
    users, pageable, totalElements);
```

## 🗂️ Exception Handling

### **Business Exceptions**
```java
// Business rule violations
throw new BusinessRuleViolationException("Email already exists");

// Entity not found
throw new EntityNotFoundException("User not found with ID: " + id);

// Validation errors (handled by @Valid automatically)
```

### **Global Exception Handler** (Automatic)
```java
// All exceptions are automatically handled and return ApiResponse format
{
  "success": false,
  "error": {
    "code": "BUSINESS_RULE_VIOLATION",
    "message": "Email already exists"
  },
  "timestamp": "2024-01-15T10:30:00Z"
}
```

## 🚀 Development Commands

### **Build & Run**
```bash
# Compile and run
mvn spring-boot:run

# Run tests
mvn test

# Run architecture tests
mvn test -Dtest=ArchitectureTest

# Package application
mvn clean package
```

### **Testing**
```bash
# Run all tests
mvn test

# Run with coverage
mvn test jacoco:report

# Test specific class
mvn test -Dtest=CreateUserCommandHandlerTest
```

### **Database**
```bash
# Access H2 Console
http://localhost:8080/h2-console
# JDBC URL: jdbc:h2:mem:testdb
# Username: sa
# Password: (empty)
```

## 📊 Common Patterns

### **Adding New Feature**
1. **Domain**: Create entity/value object in `domain/model/`
2. **Application**: Create command/query + handler in `application/`  
3. **Infrastructure**: Create repository in `infrastructure/database/`
4. **Presentation**: Create controller + DTOs in `presentation/`

### **Using Infrastructure Services**
```java
@Service
public class OrderService {
    private final CacheService cacheService;
    private final ExternalApiService externalApiService;
    private final MessagingService messagingService;
    
    public OrderDto processOrder(CreateOrderCommand command) {
        // Use interface, not implementation
        PaymentResponse payment = externalApiService.post(
            "/api/payment", command.getPaymentInfo(), PaymentResponse.class);
        
        messagingService.sendMessage("order.created", orderEvent);
        cacheService.put("order:" + orderId, order);
        
        return OrderMapper.toDto(order);
    }
}
```

### **Error Handling Best Practices**
```java
// Use specific exceptions
if (userRepository.existsByEmail(email)) {
    throw new BusinessRuleViolationException("Email already exists");
}

// EntityNotFoundException for missing resources
User user = userRepository.findById(id)
    .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));

// ValidationException for input validation
if (StringUtils.isBlank(firstName)) {
    throw new ValidationException("First name cannot be blank");
}
```

### **CQRS Pattern Examples**

**Command Handler:**
```java
@Service
@Transactional
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand, Long> {
    
    private final UserRepository userRepository;
    private final DomainEventPublisher eventPublisher;
    
    @Override
    public Long handle(CreateUserCommand command) {
        // 1. Validate business rules
        if (userRepository.existsByEmail(command.getEmail())) {
            throw new BusinessRuleViolationException("Email already exists");
        }
        
        // 2. Create domain object
        User user = User.create(
            command.getFirstName(),
            command.getLastName(),
            Email.of(command.getEmail())
        );
        
        // 3. Save to repository
        User savedUser = userRepository.save(user);
        
        // 4. Publish events
        eventPublisher.publishAll(savedUser.getDomainEvents());
        
        return savedUser.getId();
    }
}
```

**Query Handler:**
```java
@Service
@Transactional(readOnly = true)
public class GetUserByIdQueryHandler implements QueryHandler<GetUserByIdQuery, UserDto> {
    
    private final UserRepository userRepository;
    private final CacheService cacheService;
    
    @Override
    public UserDto handle(GetUserByIdQuery query) {
        // Try cache first
        Optional<Object> cached = cacheService.get("user:" + query.getUserId());
        if (cached.isPresent()) {
            return (UserDto) cached.get();
        }
        
        // Fallback to database
        User user = userRepository.findById(query.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        UserDto userDto = UserMapper.toDto(user);
        cacheService.put("user:" + user.getId(), userDto);
        
        return userDto;
    }
}
```

### 3. Presentation Layer

**Controller:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @PostMapping
    public ResponseEntity<ApiResponse<CreateUserResponse>> createUser(
        @Valid @RequestBody CreateUserRequest request) {
        
        CreateUserCommand command = new CreateUserCommand();
        // Map request to command
        Long userId = useCaseDispatcher.dispatch(command);
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(new CreateUserResponse(userId)));
    }
}
```

## Development Workflow

### 1. Thêm Feature Mới

1. **Tạo Command/Query:**
   ```java
   public class UpdateUserCommand implements Command<Void> {
       private Long userId;
       private String firstName;
       private String lastName;
       // getters/setters
   }
   ```

2. **Tạo Handler:**
   ```java
   @Service
   @Transactional
   public class UpdateUserCommandHandler implements CommandHandler<UpdateUserCommand, Void> {
       @Override
       public Void handle(UpdateUserCommand command) {
           // Implementation
           return null;
       }
   }
   ```

3. **Thêm Endpoint:**
   ```java
   @PutMapping("/{id}")
   public ResponseEntity<ApiResponse<Void>> updateUser(
       @PathVariable Long id,
       @Valid @RequestBody UpdateUserRequest request) {
       // Implementation
   }
   ```

### 2. Database Migration

1. **Thêm Entity Field:**
   ```java
   @Column
   private String phoneNumber;
   ```

2. **Update data.sql** (nếu cần):
   ```sql
   INSERT INTO users (first_name, last_name, email, phone_number) 
   VALUES ('John', 'Doe', 'john@example.com', '+1234567890');
   ```

### 3. Testing

**Unit Test (Domain):**
```java
@Test
void should_create_user_with_valid_data() {
    User user = User.create("John", "Doe", Email.of("john@example.com"));
    
    assertEquals("John", user.getFirstName());
    assertTrue(user.hasDomainEvents());
}
```

**Integration Test (API):**
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {
    
    @Test
    void should_create_user_successfully() {
        CreateUserRequest request = new CreateUserRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@example.com");
        
        ResponseEntity<ApiResponse<CreateUserResponse>> response = 
            restTemplate.postForEntity("/api/users", request, 
                new ParameterizedTypeReference<ApiResponse<CreateUserResponse>>() {});
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
```

## Common Patterns

### 1. Domain Events

**Tạo Event:**
```java
public class UserCreatedEvent extends DomainEvent {
    private final String firstName;
    private final String lastName;
    private final Email email;
    
    // constructor, getters
}
```

**Event Handler:**
```java
@Component
public class UserCreatedEventHandler implements DomainEventHandler<UserCreatedEvent> {
    
    @Override
    public void handle(UserCreatedEvent event) {
        log.info("User created: {} {}", event.getFirstName(), event.getLastName());
        // Send welcome email, create profile, etc.
    }
}
```

### 2. Exception Handling

**Business Exception:**
```java
throw new BusinessRuleViolationException("User with email " + email + " already exists");
```

**Global Handler:**
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BusinessRuleViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessRuleViolation(
        BusinessRuleViolationException ex) {
        return ResponseEntity.badRequest()
            .body(ApiResponse.error(ex.getMessage()));
    }
}
```

### 3. Validation

**Request Validation:**
```java
public class CreateUserRequest {
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50)
    private String firstName;
    
    @Email(message = "Email must be valid")
    private String email;
}
```

**Domain Validation:**
```java
private String validateAndTrimName(String name, String fieldName) {
    if (name == null || name.trim().isEmpty()) {
        throw new BusinessRuleViolationException(fieldName + " cannot be null or empty");
    }
    
    String trimmedName = name.trim();
    if (trimmedName.length() < 2 || trimmedName.length() > 50) {
        throw new BusinessRuleViolationException(fieldName + " must be between 2 and 50 characters");
    }
    
    return trimmedName;
}
```

## Useful Commands

```bash
# Build project
./mvnw clean compile

# Run tests
./mvnw test

# Run application
./mvnw spring-boot:run

# Package application
./mvnw package

# Run specific test
./mvnw test -Dtest=UserTest

# Run with specific profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

## Monitoring & Debug

**Health Check:**
```bash
curl http://localhost:8080/actuator/health
```

**Application Info:**
```bash
curl http://localhost:8080/actuator/info
```

**Database Console:**
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)

**API Documentation:**
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
