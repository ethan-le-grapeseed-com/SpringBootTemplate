# Annotations Reference

## Mục lục
1. [Spring Boot Annotations](#spring-boot-annotations)
2. [Spring MVC Annotations](#spring-mvc-annotations)
3. [JPA/Hibernate Annotations](#jpahibernate-annotations)
4. [Validation Annotations](#validation-annotations)
5. [Lombok Annotations](#lombok-annotations)
6. [OpenAPI/Swagger Annotations](#openapiswagger-annotations)
7. [Testing Annotations](#testing-annotations)

---

## Spring Boot Annotations

### @SpringBootApplication
- **Package**: `org.springframework.boot.autoconfigure`
- **Mục đích**: Annotation chính để khởi động Spring Boot application
- **Tính năng**: Kết hợp `@Configuration`, `@EnableAutoConfiguration`, và `@ComponentScan`
- **Ví dụ**:
```java
@SpringBootApplication
public class SpringBootTemplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootTemplateApplication.class, args);
    }
}
```

### @RestController
- **Package**: `org.springframework.web.bind.annotation`
- **Mục đích**: Đánh dấu class là REST controller
- **Tính năng**: Kết hợp `@Controller` và `@ResponseBody`
- **Sử dụng**: Controller layer trong presentation layer

### @RequestMapping
- **Package**: `org.springframework.web.bind.annotation`
- **Mục đích**: Mapping HTTP requests đến handler methods
- **Thuộc tính**: 
  - `value/path`: URL path
  - `method`: HTTP method
- **Ví dụ**: `@RequestMapping("/api/users")`

### @GetMapping, @PostMapping
- **Package**: `org.springframework.web.bind.annotation`
- **Mục đích**: Shortcut cho `@RequestMapping` với HTTP method cụ thể
- **Sử dụng**: Mapping GET/POST requests

### @PathVariable
- **Package**: `org.springframework.web.bind.annotation`
- **Mục đích**: Bind URL path variables đến method parameters
- **Ví dụ**: `@GetMapping("/{id}")` với `@PathVariable Long id`

### @RequestBody
- **Package**: `org.springframework.web.bind.annotation`
- **Mục đích**: Bind HTTP request body đến object parameter
- **Sử dụng**: Thường dùng với POST/PUT requests

---

## Spring MVC Annotations

### @Valid
- **Package**: `jakarta.validation`
- **Mục đích**: Trigger validation trên object
- **Sử dụng**: Validate request bodies, method parameters
- **Ví dụ**: `@Valid @RequestBody CreateUserRequest request`

### @RestControllerAdvice
- **Package**: `org.springframework.web.bind.annotation`
- **Mục đích**: Global exception handling cho REST controllers
- **Tính năng**: Xử lý exceptions across toàn bộ application

### @ExceptionHandler
- **Package**: `org.springframework.web.bind.annotation`
- **Mục đích**: Định nghĩa method để handle specific exceptions
- **Sử dụng**: Trong `@RestControllerAdvice` classes

---

## JPA/Hibernate Annotations

### @Entity
- **Package**: `jakarta.persistence`
- **Mục đích**: Đánh dấu class là JPA entity
- **Yêu cầu**: Class phải có default constructor

### @Table
- **Package**: `jakarta.persistence`
- **Mục đích**: Specify table name và properties
- **Thuộc tính**: `name`, `schema`, `uniqueConstraints`
- **Ví dụ**: `@Table(name = "users")`

### @Id
- **Package**: `jakarta.persistence`
- **Mục đích**: Đánh dấu field là primary key
- **Yêu cầu**: Mỗi entity phải có một `@Id`

### @GeneratedValue
- **Package**: `jakarta.persistence`
- **Mục đích**: Specify strategy cho auto-generation của primary key
- **Strategies**: `IDENTITY`, `SEQUENCE`, `TABLE`, `AUTO`
- **Ví dụ**: `@GeneratedValue(strategy = GenerationType.IDENTITY)`

### @Column
- **Package**: `jakarta.persistence`
- **Mục đích**: Specify column properties
- **Thuộc tính**: 
  - `name`: Column name
  - `nullable`: Có thể null không
  - `unique`: Unique constraint
  - `length`: Max length
- **Ví dụ**: `@Column(name = "email", nullable = false, unique = true)`

---

## Validation Annotations

### @NotBlank
- **Package**: `jakarta.validation.constraints`
- **Mục đích**: Validate string không null, không empty và không chỉ chứa whitespace
- **Sử dụng**: String fields
- **Ví dụ**: `@NotBlank(message = "First name is required")`

### @Size
- **Package**: `jakarta.validation.constraints`
- **Mục đích**: Validate size của string, collection, array
- **Thuộc tính**: `min`, `max`
- **Ví dụ**: `@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")`

### @Email
- **Package**: `jakarta.validation.constraints`
- **Mục đích**: Validate email format
- **Sử dụng**: String fields chứa email
- **Ví dụ**: `@Email(message = "Email must be valid")`

### @NotNull
- **Package**: `jakarta.validation.constraints`
- **Mục đích**: Validate field không null
- **Sử dụng**: Bất kỳ object nào

---

## Lombok Annotations

### @Data
- **Package**: `lombok`
- **Mục đích**: Generate getters, setters, toString, equals, hashCode
- **Tính năng**: All-in-one annotation cho data classes
- **Sử dụng**: DTO, Request/Response classes

### @Getter
- **Package**: `lombok`
- **Mục đích**: Generate getter methods
- **Scope**: Class level hoặc field level

### @RequiredArgsConstructor
- **Package**: `lombok`
- **Mục đích**: Generate constructor với required fields (final fields và @NonNull fields)
- **Sử dụng**: Dependency injection, immutable objects

### @Slf4j
- **Package**: `lombok.extern.slf4j`
- **Mục đích**: Generate SLF4J logger field
- **Tạo**: `private static final Logger log = LoggerFactory.getLogger(ClassName.class)`

---

## OpenAPI/Swagger Annotations

### @Tag
- **Package**: `io.swagger.v3.oas.annotations.tags`
- **Mục đích**: Group operations trong Swagger UI
- **Thuộc tính**: `name`, `description`
- **Ví dụ**: `@Tag(name = "Users", description = "User management operations")`

### @Operation
- **Package**: `io.swagger.v3.oas.annotations`
- **Mục đích**: Describe API operation
- **Thuộc tính**: `summary`, `description`, `responses`
- **Ví dụ**: `@Operation(summary = "Get all users")`

---

## Testing Annotations

### @Test
- **Package**: `org.junit.jupiter.api`
- **Mục đích**: Đánh dấu method là test method
- **Framework**: JUnit 5

---

## JSON Annotations

### @JsonInclude
- **Package**: `com.fasterxml.jackson.annotation`
- **Mục đích**: Control serialization của null/empty values
- **Values**: `NON_NULL`, `NON_EMPTY`, `ALWAYS`
- **Ví dụ**: `@JsonInclude(JsonInclude.Include.NON_NULL)`

---

## Best Practices

### 1. Validation Annotations
- Luôn sử dụng `message` attribute để cung cấp error messages rõ ràng
- Combine multiple validation annotations khi cần thiết
- Sử dụng `@Valid` để trigger validation

### 2. JPA Annotations
- Sử dụng `@Table(name = "table_name")` để explicit table names
- Luôn specify `nullable = false` cho required fields
- Sử dụng `unique = true` cho unique constraints

### 3. Lombok Annotations
- Tránh sử dụng `@Data` cho entities (có thể gây issues với lazy loading)
- Sử dụng `@Getter` thay vì `@Data` cho entities
- `@RequiredArgsConstructor` tốt cho dependency injection

### 4. Spring Annotations
- Sử dụng specific mapping annotations (`@GetMapping`, `@PostMapping`) thay vì `@RequestMapping`
- `@RestControllerAdvice` cho global exception handling
- `@Valid` cho validation tự động

### 5. Code Organization
- Group related annotations together
- Place validation annotations close to field declarations
- Use meaningful names trong `message` attributes

---

## Common Patterns trong Project

### 1. Controller Pattern
```java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management operations")
public class UserController {
    
    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<ApiResponse<CreateUserResponse>> createUser(
        @Valid @RequestBody CreateUserRequest request) {
        // implementation
    }
}
```

### 2. Entity Pattern
```java
@Entity
@Table(name = "users")
@Getter
public class User extends AggregateRoot<Long> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;
}
```

### 3. Request DTO Pattern
```java
@Data
public class CreateUserRequest {
    
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
}
```

### 4. Response DTO Pattern
```java
@Getter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private final boolean success;
    private final T data;
    private final String message;
    private final String error;
}
```

---

*Document này được tạo tự động dựa trên analysis của Spring Boot Template project.*
