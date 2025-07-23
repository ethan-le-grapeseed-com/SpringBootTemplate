# Infrastructure Layer Architecture

## Tổng quan

Infrastructure layer đã được phân tách thành các sub-layers chuyên biệt để đảm bảo tính modular và separation of concerns:

## Cấu trúc Infrastructure Layers

### 1. Database Layer (`database/`)
**Mục đích**: Quản lý tất cả các tương tác với database
- **Config**: `DatabaseConfig.java` - Cấu hình JPA, repositories, transactions
- **Adapter**: `DatabaseRepositoryAdapter.java` - Base adapter cho database operations
- **Repository**: Các JPA repositories cụ thể

**Đặc điểm**:
- Transactional operations
- JPA/Hibernate integration
- Repository pattern implementation
- Database-specific configurations

### 2. Cache Layer (`cache/`)
**Mục đích**: Quản lý caching strategies và operations
- **Config**: `CacheConfig.java` - Cấu hình cache managers
- **Adapter**: `CacheAdapter.java` - Type-safe cache operations

**Đặc điểm**:
- Spring Cache abstraction
- Multiple cache backends support
- Type-safe operations
- Cache eviction strategies

### 3. External API Layer (`external/`)
**Mục đích**: Xử lý tất cả các external API calls với resilience patterns
- **Config**: `ExternalApiConfig.java` - Circuit breaker, retry configurations
- **Adapter**: `ExternalApiAdapter.java` - Resilient HTTP client
- **Exception**: `ExternalApiException.java` - Specialized exceptions

**Đặc điểm**:
- Circuit breaker pattern (Resilience4j)
- Retry mechanism with exponential backoff
- Transient fault handling
- Comprehensive error handling và logging

### 4. Messaging Layer (`messaging/`)
**Mục đích**: Quản lý internal events và external messaging
- **Config**: `MessagingConfig.java` - Message broker configurations
- **Adapter**: `MessagingAdapter.java` - Event publishing và messaging
- **Publisher**: `SpringDomainEventPublisher.java` - Domain event publishing

**Đặc điểm**:
- Domain event publishing
- External message broker integration (future)
- Asynchronous processing support
- Event-driven architecture support

### 5. Security Layer (`security/`)
**Mục đích**: Xử lý authentication, authorization, và data protection
- **Config**: `SecurityConfig.java` - Security policies
- **Adapter**: `SecurityAdapter.java` - Security operations

**Đặc điểm**:
- User context management
- Permission checking
- Data encryption/decryption
- Security audit logging

## Resilience Patterns

### Circuit Breaker
```java
// Cấu hình trong ExternalApiConfig
CircuitBreakerConfig.custom()
    .failureRateThreshold(50)           // 50% failure rate triggers open
    .waitDurationInOpenState(1000ms)    // Wait 1s before half-open
    .slidingWindowSize(2)               // Monitor last 2 calls
    .minimumNumberOfCalls(2)            // Min calls before evaluation
```

### Retry Mechanism
```java
// Cấu hình retry
RetryConfig.custom()
    .maxAttempts(3)                     // Maximum 3 attempts
    .waitDuration(500ms)                // Wait 500ms between retries
```

### Transient Fault Handling
- Network timeouts
- HTTP 5xx errors
- Connection failures
- Service unavailability

## Usage Examples

### Database Operations
```java
@Service
public class UserService {
    private final UserRepository userRepository; // Uses DatabaseRepositoryAdapter
    
    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
```

### Cache Operations
```java
@Service
public class UserService {
    private final CacheAdapter cacheAdapter;
    
    public Optional<User> getUser(Long id) {
        return cacheAdapter.get("users", "user:" + id, User.class);
    }
}
```

### External API Calls
```java
@Service
public class UserService {
    private final ExternalApiAdapter externalApiAdapter;
    
    public void validateUser(User user) {
        ResponseEntity<String> response = externalApiAdapter.get(
            "validation-service", 
            "https://api.example.com/validate/" + user.getId(),
            String.class
        );
    }
}
```

### Messaging
```java
@Service
public class UserService {
    private final MessagingAdapter messagingAdapter;
    
    public void notifyUserCreated(User user) {
        messagingAdapter.publishMessage("user.events", 
            new UserCreatedNotification(user.getId()));
    }
}
```

## Benefits

1. **Separation of Concerns**: Mỗi layer có responsibility cụ thể
2. **Resilience**: Built-in fault tolerance cho external calls
3. **Testability**: Dễ dàng mock từng layer độc lập
4. **Scalability**: Có thể scale từng layer theo nhu cầu
5. **Maintainability**: Code được tổ chức rõ ràng theo chức năng
6. **Extensibility**: Dễ dàng thêm features mới cho từng layer

## Migration Notes

- Old `JpaRepositoryAdapter` → New `DatabaseRepositoryAdapter`
- Old `SpringDomainEventPublisher` → Moved to `messaging/publisher/`
- External API calls nên sử dụng `ExternalApiAdapter` thay vì direct RestTemplate
- Cache operations nên sử dụng `CacheAdapter` thay vì direct Spring Cache annotations

## Future Enhancements

1. **Database Layer**: Multi-tenancy, read/write splitting
2. **Cache Layer**: Distributed caching (Redis, Hazelcast)
3. **External API Layer**: OAuth2, API rate limiting
4. **Messaging Layer**: Message brokers (RabbitMQ, Kafka)
5. **Security Layer**: JWT, OAuth2, encryption at rest
