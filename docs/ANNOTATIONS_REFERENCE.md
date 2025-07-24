# 🏷️ Tham khảo Annotations

## 📋 Mục lục
1. [Spring Boot Annotations](#spring-boot-annotations)
2. [Spring MVC Annotations](#spring-mvc-annotations) 
3. [JPA/Hibernate Annotations](#jpahibernate-annotations)
4. [Validation Annotations](#validation-annotations)
5. [Lombok Annotations](#lombok-annotations)
6. [MapStruct Annotations](#mapstruct-annotations)
7. [Testing Annotations](#testing-annotations)
8. [Clean Architecture Annotations](#clean-architecture-annotations)

---

## 🚀 Spring Boot Annotations

### @SpringBootApplication
- **Package**: `org.springframework.boot.autoconfigure`
- **Mục đích**: Annotation chính để bootstrap Spring Boot application
- **Tính năng**: Kết hợp `@Configuration`, `@EnableAutoConfiguration`, và `@ComponentScan`
- **Sử dụng**: Application entry point
```java
@SpringBootApplication
public class SpringBootTemplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootTemplateApplication.class, args);
    }
}
```

### @Configuration
- **Package**: `org.springframework.context.annotation`
- **Mục đích**: Chỉ ra class chứa bean definitions
- **Sử dụng**: Core infrastructure configuration
```java
@Configuration
@EnableJpaRepositories
@EnableCaching
public class UseCaseConfig {
    @Bean
    public CacheService cacheService(CacheManager cacheManager) {
        return new CacheServiceImpl(cacheManager);
    }
}
```

### @Service
- **Package**: `org.springframework.stereotype`
- **Mục đích**: Đánh dấu class là service layer component
- **Sử dụng**: Application services, command/query handlers
```java
@Service
@Transactional
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand, UserDto> {
    // Implementation
}
```

### @Component
- **Package**: `org.springframework.stereotype`
- **Mục đích**: Generic stereotype cho Spring-managed components
- **Sử dụng**: Infrastructure adapters, utilities
```java
@Component
public class CacheAdapter {
    // Cache operations
}
```

---

## 🌐 Spring MVC Annotations

### @RestController
- **Package**: `org.springframework.web.bind.annotation`
- **Mục đích**: Đánh dấu class là REST controller
- **Tính năng**: Kết hợp `@Controller` và `@ResponseBody`
- **Sử dụng**: Presentation layer controllers
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    // REST endpoints
}
```

### @RequestMapping
- **Package**: `org.springframework.web.bind.annotation`
- **Mục đích**: Maps HTTP requests đến handler methods
- **Thuộc tính**: 
  - `value/path`: URL path
  - `method`: HTTP method
  - `produces/consumes`: Content types
```java
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
```

### @GetMapping, @PostMapping, @PutMapping, @DeleteMapping
- **Package**: `org.springframework.web.bind.annotation`
- **Mục đích**: Shortcut cho `@RequestMapping` với HTTP methods cụ thể
- **Sử dụng**: RESTful endpoint mapping
```java
@GetMapping("/{id}")
public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
    // Implementation
}

@PostMapping
public ResponseEntity<ApiResponse<CreateUserResponse>> createUser(
        @Valid @RequestBody CreateUserRequest request) {
    // Implementation
}
```

### @PathVariable
- **Package**: `org.springframework.web.bind.annotation`
- **Mục đích**: Extracts values từ URI path
- **Sử dụng**: RESTful path parameters
```java
@GetMapping("/{id}")
public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
    // id extracted from /api/users/123
}
```

### @RequestBody
- **Package**: `org.springframework.web.bind.annotation`
- **Mục đích**: Binds HTTP request body đến method parameter
- **Sử dụng**: JSON request payload
```java
@PostMapping
public ResponseEntity<ApiResponse<CreateUserResponse>> createUser(
        @Valid @RequestBody CreateUserRequest request) {
    // request mapped from JSON
}
```

### @Valid
- **Package**: `javax.validation`
- **Mục đích**: Triggers validation trên method parameters
- **Sử dụng**: Request validation với Bean Validation
```java
@PostMapping
public ResponseEntity<ApiResponse<CreateUserResponse>> createUser(
        @Valid @RequestBody CreateUserRequest request) {
    // Validates request using @NotBlank, @Email, etc.
}
```

---

## 🗃️ JPA/Hibernate Annotations

### @Entity
- **Package**: `javax.persistence`
- **Mục đích**: Đánh dấu class là JPA entity
- **Sử dụng**: Domain entities, database entities
```java
@Entity
@Table(name = "users")
public class UserEntity {
    // Entity fields
}
```

### @Table
- **Package**: `javax.persistence`
- **Mục đích**: Specifies database table details
- **Thuộc tính**: `name`, `schema`, `indexes`
```java
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_user_email", columnList = "email", unique = true)
})
public class UserEntity {
    // Entity definition
}
```

### @Id
- **Package**: `javax.persistence`
- **Mục đích**: Đánh dấu primary key field
- **Sử dụng**: Entity identifier
```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

### @GeneratedValue
- **Package**: `javax.persistence`
- **Mục đích**: Specifies primary key generation strategy
- **Strategies**: `IDENTITY`, `SEQUENCE`, `TABLE`, `AUTO`
```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

### @Column
- **Package**: `javax.persistence`
- **Mục đích**: Specifies column mapping details
- **Thuộc tính**: `name`, `nullable`, `unique`, `length`
```java
@Column(name = "first_name", nullable = false, length = 50)
private String firstName;

@Column(name = "email", nullable = false, unique = true)
private String email;
```

### @Embedded
- **Package**: `javax.persistence`
- **Mục đích**: Embeds value object vào entity
- **Sử dụng**: Value objects trong domain entities
```java
@Embedded
private Email email;
```

### @Embeddable
- **Package**: `javax.persistence`
- **Mục đích**: Đánh dấu class là embeddable value object
- **Sử dụng**: Value object classes
```java
@Embeddable
public class EmailEmbeddable {
    @Column(name = "email", nullable = false, unique = true)
    private String value;
}
```

---

## ✅ Validation Annotations

### @NotNull
- **Package**: `javax.validation.constraints`
- **Mục đích**: Validates rằng field không null
- **Sử dụng**: Required fields
```java
@NotNull(message = "User ID cannot be null")
private Long userId;
```

### @NotBlank
- **Package**: `javax.validation.constraints`
- **Mục đích**: Validates rằng string không null, empty, hoặc whitespace
- **Sử dụng**: Required string fields
```java
@NotBlank(message = "First name cannot be blank")
@Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
private String firstName;
```

### @Email
- **Package**: `javax.validation.constraints`
- **Mục đích**: Validates email format
- **Sử dụng**: Email fields
```java
@Email(message = "Invalid email format")
@NotBlank(message = "Email cannot be blank")
private String email;
```

### @Size
- **Package**: `javax.validation.constraints`
- **Mục đích**: Validates string/collection size
- **Thuộc tính**: `min`, `max`, `message`
```java
@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
private String firstName;
```

### @Min, @Max
- **Package**: `javax.validation.constraints`
- **Mục đích**: Validates numeric ranges
- **Sử dụng**: Numeric field validation
```java
@Min(value = 1, message = "Page number must be at least 1")
private Integer page;

@Max(value = 100, message = "Page size cannot exceed 100")
private Integer size;
```

---

## 🔧 Lombok Annotations

### @Data
- **Package**: `lombok`
- **Mục đích**: Generates getters, setters, equals, hashCode, toString
- **Sử dụng**: Data classes, DTOs
```java
@Data
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
```

### @Builder
- **Package**: `lombok`
- **Mục đích**: Generates builder pattern
- **Sử dụng**: Object construction với nhiều parameters
```java
@Data
@Builder
public class CreateUserCommand {
    private String firstName;
    private String lastName;
    private String email;
}

// Usage
CreateUserCommand command = CreateUserCommand.builder()
    .firstName("John")
    .lastName("Doe")
    .email("john@example.com")
    .build();
```

### @AllArgsConstructor, @NoArgsConstructor
- **Package**: `lombok`
- **Mục đích**: Generates constructors
- **Sử dụng**: JPA entities, immutable classes
```java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    // Fields
}
```

### @RequiredArgsConstructor
- **Package**: `lombok`
- **Mục đích**: Generates constructor cho final fields
- **Sử dụng**: Service classes với dependency injection
```java
@Service
@RequiredArgsConstructor
public class CreateUserCommandHandler {
    private final UserRepository userRepository;
    private final CacheService cacheService;
    
        // Constructor automatically generated
}
```

---

## 🗺️ MapStruct Annotations

### @Mapper
- **Package**: `org.mapstruct`
- **Mục đích**: Đánh dấu interface là MapStruct mapper
- **Sử dụng**: Object mapping giữa các layers
```java
@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDto> {
    
    @Mapping(source = "emailValue", target = "email")
    UserDto toDto(User user);
    
    @Mapping(source = "email", target = "emailValue")
    User toEntity(UserDto userDto);
    
    UserResponse toResponse(UserDto userDto);
    CreateUserResponse toCreateResponse(UserDto userDto);
}
```

### @Mapping
- **Package**: `org.mapstruct`
- **Mục đích**: Cấu hình mapping giữa các fields
- **Thuộc tính**: `source`, `target`, `ignore`, `expression`
```java
@Mapping(source = "emailValue", target = "email")
@Mapping(source = "firstName", target = "fullName", expression = "java(user.getFirstName() + ' ' + user.getLastName())")
@Mapping(target = "id", ignore = true)
UserDto toDto(User user);
```

---

## 🧪 Testing Annotations

### @SpringBootTest
- **Package**: `org.springframework.boot.test.context`
- **Mục đích**: Loads complete Spring application context cho integration tests
- **Sử dụng**: Integration tests
```java
@SpringBootTest
@Transactional
class UserIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void shouldCreateUser() {
        // Integration test
    }
}
```

### @WebMvcTest
- **Package**: `org.springframework.boot.test.autoconfigure.web.servlet`
- **Mục đích**: Tests web layer only (controllers)
- **Sử dụng**: Controller unit tests
```java
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UseCaseDispatcher useCaseDispatcher;
    
    @Test
    void shouldCreateUser() throws Exception {
        // Controller test
    }
}
```

### @DataJpaTest
- **Package**: `org.springframework.boot.test.autoconfigure.orm.jpa`
- **Mục đích**: Tests JPA repositories
- **Sử dụng**: Repository tests
```java
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private UserJpaRepository userRepository;
    
    @Test
    void shouldFindUserByEmail() {
        // Repository test
    }
}
```

### @Mock, @MockBean
- **Package**: `org.mockito`, `org.springframework.boot.test.mock.mockito`
- **Mục đích**: Creates mock objects cho testing
- **Sử dụng**: Unit tests với mocked dependencies
```java
@ExtendWith(MockitoExtension.class)
class CreateUserCommandHandlerTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private CacheService cacheService;
    
    @InjectMocks
    private CreateUserCommandHandler handler;
    
    @Test
    void shouldCreateUser() {
        // Unit test with mocks
    }
}
```

### @Transactional
- **Package**: `org.springframework.transaction.annotation`
- **Mục đích**: Enables transaction management
- **Sử dụng**: Service methods, test methods
```java
@Service
@Transactional
public class CreateUserCommandHandler {
    // All methods are transactional
    
    @Transactional(readOnly = true)
    public UserDto getUser(Long id) {
        // Read-only transaction
    }
}
```

---

## 🏗️ Clean Architecture Annotations

### Custom Architecture Annotations
```java
// Command marker
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
}

// Query marker
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {
}

// Usage
@Command
@Data
@Builder
public class CreateUserCommand {
    private String firstName;
    private String lastName;
    private String email;
}

@Query
@Data
@Builder
public class GetUserByIdQuery {
    private Long userId;
}
```

---

## 📊 Exception Handling Annotations

### @RestControllerAdvice
- **Package**: `org.springframework.web.bind.annotation`
- **Mục đích**: Global exception handling cho REST controllers
- **Sử dụng**: GlobalExceptionHandler (core component)
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BusinessRuleViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessRuleViolation(
            BusinessRuleViolationException ex) {
        return ResponseEntity.badRequest()
            .body(ApiResponse.error("BUSINESS_RULE_VIOLATION", ex.getMessage()));
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEntityNotFound(
            EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.error("ENTITY_NOT_FOUND", ex.getMessage()));
    }
}
```

### @ExceptionHandler
- **Package**: `org.springframework.web.bind.annotation`
- **Mục đích**: Handles specific exception types
- **Sử dụng**: Exception handling methods
```java
@ExceptionHandler({ValidationException.class, MethodArgumentNotValidException.class})
public ResponseEntity<ApiResponse<Void>> handleValidation(Exception ex) {
    return ResponseEntity.badRequest()
        .body(ApiResponse.error("VALIDATION_ERROR", ex.getMessage()));
}
```

---

## 🎯 Best Practices

### **Annotations theo Layer**
```java
// Domain Layer - Minimal annotations
@Entity
@Data
@NoArgsConstructor
public class User extends AggregateRoot<Long> {
    // Pure business logic
}

// Application Layer - Service annotations
@Service
@RequiredArgsConstructor
@Transactional
public class CreateUserCommandHandler {
    // Use case implementation
}

// Infrastructure Layer - Technical annotations
@Repository
@Component
public class CacheAdapter {
    // Technical implementation
}

// Presentation Layer - Web annotations
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    // REST endpoints
}
```

### **Kết hợp Validation**
```java
// Request DTOs
@Data
@Builder
public class CreateUserRequest {
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    
    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
    
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;
}
```

### **Kết hợp Testing Annotations**
```java
// Integration Test
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserIntegrationTest {
    // Full context test
}

// Unit Test
@ExtendWith(MockitoExtension.class)
class CreateUserCommandHandlerTest {
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private CreateUserCommandHandler handler;
    
    // Isolated unit test
}
```

---

*Tài liệu này được tạo tự động dựa trên phân tích của Spring Boot Template project.*
