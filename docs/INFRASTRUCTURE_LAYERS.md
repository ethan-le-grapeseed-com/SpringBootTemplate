# Kiến trúc Infrastructure Layer

## 📋 Tổng quan

Infrastructure layer được thiết kế với các nguyên tắc Clean Architecture, triển khai **Service Interface Pattern** để duy trì sự tách biệt giữa application logic và các chi tiết technical implementation.

## 🏗️ Kiến trúc Infrastructure Service

### **Service Interface Pattern**
```java
// Application Layer (Interfaces)
public interface CacheService {
    void put(String key, Object value);
    Optional<Object> get(String key);
    void evict(String key);
}

// Infrastructure Layer (Implementations) 
@Service
public class CacheServiceImpl implements CacheService {
    private final CacheManager cacheManager;
    
    @Override
    public void put(String key, Object value) {
        cacheManager.getCache("default").put(key, value);
    }
}

// Configuration (Dependency Injection)
@Configuration
public class UseCaseConfig {
    @Bean
    public CacheService cacheService(CacheManager cacheManager) {
        return new CacheServiceImpl(cacheManager);
    }
}
```

## 🔧 Cấu trúc Infrastructure Layers

### 1. **Application Service Interfaces** (`core/application/service/`)
**Mục đích**: Định nghĩa các contracts cho infrastructure operations

```java
// Cache operations
public interface CacheService {
    void put(String key, Object value);
    Optional<Object> get(String key);
    void evict(String key);
    void clear();
}

// External API operations
public interface ExternalApiService {
    <T> T get(String url, Class<T> responseType);
    <T> T post(String url, Object request, Class<T> responseType);
    void putWithCircuitBreaker(String url, Object request);
}

// Messaging operations
public interface MessagingService {
    void sendMessage(String topic, Object message);
    void subscribe(String topic, MessageHandler handler);
}

// Security operations
public interface SecurityService {
    String getCurrentUserId();
    boolean hasPermission(String permission);
    void authenticate(String token);
}
```

### 2. **Infrastructure Service Implementations** (`core/infrastructure/service/`)
**Mục đích**: Cung cấp technical implementations của application interfaces

#### **CacheServiceImpl** - Caching Layer
```java
@Service
public class CacheServiceImpl implements CacheService {
    private final CacheManager cacheManager;
    
    @Override
    public void put(String key, Object value) {
        Cache cache = cacheManager.getCache("default");
        if (cache != null) {
            cache.put(key, value);
        }
    }
    
    @Override
    public Optional<Object> get(String key) {
        Cache cache = cacheManager.getCache("default");
        if (cache != null) {
            Cache.ValueWrapper wrapper = cache.get(key);
            return Optional.ofNullable(wrapper != null ? wrapper.get() : null);
        }
        return Optional.empty();
    }
}
```

#### **ExternalApiServiceImpl** - External Integration Layer  
```java
@Service
public class ExternalApiServiceImpl implements ExternalApiService {
    private final RestTemplate restTemplate;
    private final CircuitBreaker circuitBreaker;
    
    @Override
    public <T> T get(String url, Class<T> responseType) {
        return circuitBreaker.executeSupplier(() -> 
            restTemplate.getForObject(url, responseType));
    }
    
    @Override
    public <T> T post(String url, Object request, Class<T> responseType) {
        return circuitBreaker.executeSupplier(() ->
            restTemplate.postForObject(url, request, responseType));
    }
#### **Database Adapter** - Data Persistence
```java
@Repository
public class DatabaseRepositoryAdapter {
    private final EntityManager entityManager;
    
    public <T> Optional<T> findById(Class<T> entityClass, Object id) {
        T entity = entityManager.find(entityClass, id);
        return Optional.ofNullable(entity);
    }
    
    public <T> T save(T entity) {
        return entityManager.merge(entity);
    }
}
```

#### **Cache Adapter** - Caching Operations
```java
@Component
public class CacheAdapter {
    private final RedisTemplate<String, Object> redisTemplate;
    
    public void put(String key, Object value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }
    
    public Optional<Object> get(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(value);
    }
}
```

#### **External API Adapter** - HTTP Client Operations
```java
@Component
public class ExternalApiAdapter {
    private final RestTemplate restTemplate;
    private final CircuitBreaker circuitBreaker;
    
    @Retryable(value = {HttpServerErrorException.class}, 
               maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public <T> ResponseEntity<T> get(String url, Class<T> responseType) {
        return circuitBreaker.executeSupplier(() -> 
            restTemplate.getForEntity(url, responseType));
    }
}
```

#### **Messaging Adapter** - Event Publishing
```java
@Component
public class MessagingAdapter {
    private final ApplicationEventPublisher eventPublisher;
    private final RabbitTemplate rabbitTemplate; // Optional external messaging
    
    public void publishDomainEvent(DomainEvent event) {
        eventPublisher.publishEvent(event);
    }
    
    public void sendExternalMessage(String exchange, String routingKey, Object message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
```

## 🔧 Configuration Layer

### **UseCaseConfig** - Main Configuration
```java
@Configuration
@EnableJpaRepositories
@EnableCaching
@EnableAsync
public class UseCaseConfig {
    
    @Bean
    public CacheService cacheService(CacheManager cacheManager) {
        return new CacheServiceImpl(cacheManager);
    }
    
    @Bean
    public ExternalApiService externalApiService(RestTemplate restTemplate, 
                                                CircuitBreaker circuitBreaker) {
        return new ExternalApiServiceImpl(restTemplate, circuitBreaker);
    }
    
    @Bean
    public MessagingService messagingService(ApplicationEventPublisher eventPublisher) {
        return new MessagingServiceImpl(eventPublisher);
    }
    
    @Bean
    public SecurityService securityService(SecurityContext securityContext) {
        return new SecurityServiceImpl(securityContext);
    }
}
```

### **WebConfig** - Web Layer Configuration
```java
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

## 🛡️ Resilience Patterns

### **Circuit Breaker Pattern**
```java
@Configuration
public class ResilienceConfig {
    
    @Bean
    public CircuitBreaker externalApiCircuitBreaker() {
        return CircuitBreaker.of("externalApi", CircuitBreakerConfig.custom()
            .failureRateThreshold(50)           // 50% failure rate triggers open
            .waitDurationInOpenState(Duration.ofMillis(1000))  // Wait 1s before half-open
            .slidingWindowSize(2)               // Monitor last 2 calls
            .minimumNumberOfCalls(2)            // Min calls before evaluation
            .build());
    }
}
```

### **Retry Mechanism**
```java
@Retryable(
    value = {HttpServerErrorException.class, ResourceAccessException.class},
    maxAttempts = 3,
    backoff = @Backoff(delay = 500, multiplier = 2)
)
public <T> T callExternalApi(String url, Class<T> responseType) {
    // External API call implementation
}
```

## 📊 Usage Examples

### **Application Service Usage**
```java
@Component
public class UserIntegrationService {
    private final CacheService cacheService;
    private final ExternalApiService externalApiService;
    private final MessagingService messagingService;
    
    public void processUser(User user) {
        // Cache user data
        cacheService.put("user:" + user.getId(), user);
        
        // Call external service
        UserValidationResponse validation = externalApiService.post(
            "/api/validate", user, UserValidationResponse.class);
        
        // Send notification
        messagingService.sendMessage("user.processed", 
            UserProcessedEvent.of(user, validation));
    }
}
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
