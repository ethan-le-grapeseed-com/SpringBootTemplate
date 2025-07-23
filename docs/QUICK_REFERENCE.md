# Quick Reference Guide

## Cấu Trúc Dự Án

```
src/main/java/com/example/
├── core/                    # Shared components
│   ├── common/             # Exceptions, validation, events
│   ├── domain/             # Base classes (Entity, AggregateRoot, Repository)
│   ├── application/        # Use case interfaces (Command, Query, Handler)
│   └── infrastructure/     # Infrastructure adapters
└── demo/                   # Example implementation
    ├── domain/             # Business logic và entities
    ├── application/        # Use case handlers và DTOs
    ├── infrastructure/     # Database repositories
    └── presentation/       # REST controllers
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/users` | Lấy danh sách users |
| `GET` | `/api/users/{id}` | Lấy user theo ID |
| `POST` | `/api/users` | Tạo user mới |

### Request/Response Examples

**Tạo User:**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","email":"john.doe@example.com"}'
```

**Response:**
```json
{
  "success": true,
  "data": {
    "userId": 1
  },
  "message": "User created successfully"
}
```

## Key Components

### 1. Domain Layer

**User Aggregate:**
```java
@Entity
public class User extends AggregateRoot<Long> {
    // Factory method
    public static User create(String firstName, String lastName, Email email) {
        User user = new User(firstName, lastName, email);
        user.addDomainEvent(new UserCreatedEvent(...));
        return user;
    }
    
    // Business methods
    public void changeEmail(Email newEmail) { ... }
    public void updateName(String firstName, String lastName) { ... }
}
```

**Email Value Object:**
```java
@Embeddable
public class Email extends ValueObject {
    public static Email of(String value) {
        return new Email(value);
    }
    
    private void validate(String value) {
        // Email validation logic
    }
}
```

### 2. Application Layer

**Command Handler:**
```java
@Service
@Transactional
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand, Long> {
    
    @Override
    public Long handle(CreateUserCommand command) {
        // 1. Validate business rules
        // 2. Create domain object
        // 3. Save to repository
        // 4. Publish events
        return savedUser.getId();
    }
}
```

**Query Handler:**
```java
@Service
@Transactional(readOnly = true)
public class GetUserByIdQueryHandler implements QueryHandler<GetUserByIdQuery, UserDto> {
    
    @Override
    public UserDto handle(GetUserByIdQuery query) {
        User user = userRepository.findById(query.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("User", query.getUserId()));
        return userMapper.toDto(user);
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
