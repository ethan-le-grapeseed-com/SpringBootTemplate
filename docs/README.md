# 📚 Tài liệu Dự án

Chào mừng đến với tài liệu Spring Boot Clean Architecture Template. Template này cung cấp một nền tảng toàn diện, có thể tái sử dụng cho các dự án Spring Boot tuân theo các nguyên tắc Clean Architecture.

## 🎯 Điểm mới

✅ **Migration Core Components**: Tất cả các functional components chung/general đã được di chuyển vào các thư mục core phù hợp để tái sử dụng trong nhiều dự án Spring Boot.

✅ **Triển khai Clean Architecture**: Triển khai đầy đủ với Service Interface Pattern để duy trì dependency inversion đúng cách.

✅ **Infrastructure có thể tái sử dụng**: Application service interfaces với infrastructure implementations cho caching, external APIs, messaging và security.

## 📚 Tài liệu Đầy đủ

### 🏗️ [Kiến trúc Hệ thống](ARCHITECTURE.md)
Mô tả chi tiết về cấu trúc dự án, các layers và các nguyên tắc thiết kế Clean Architecture.

**Nội dung:**
- **Cấu trúc Dự án**: Tổ chức thư mục hoàn chỉnh với sự tách biệt core/demo
- **Clean Architecture Layers**: Domain, Application, Infrastructure, Presentation
- **Service Interface Pattern**: CacheService, ExternalApiService, MessagingService, SecurityService
- **Design Patterns**: Repository, CQRS, Factory, Adapter patterns
- **Dependency Flow**: Dependency inversion và layer isolation đúng cách
- **Chiến lược Testing**: Architecture tests, phương pháp testing theo từng layer

### 🔄 [Luồng xử lý Ứng dụng](APPLICATION_FLOW.md)
Mô tả luồng xử lý toàn diện từ HTTP request đến response, bao gồm Command và Query flows với tích hợp Clean Architecture.

**Nội dung:**
- **Command Flow**: Create/Update operations với tích hợp infrastructure services
- **Query Flow**: Read operations với caching layer
- **Sử dụng Service Interface**: Cách application services được sử dụng trong handlers
- **Exception Handling**: Global exception handling với ApiResponse format
- **Event Processing**: Domain events và messaging integration
- **Performance Patterns**: Caching, circuit breakers, retry mechanisms

### 🛠️ [Infrastructure Layers](INFRASTRUCTURE_LAYERS.md)
Bảo hiểm chi tiết về infrastructure layers và triển khai Service Interface Pattern.

**Nội dung:**
- **Application Service Interfaces**: Định nghĩa contracts cho infrastructure operations
- **Infrastructure Service Implementations**: Technical implementations của application interfaces
- **Infrastructure Adapters**: Low-level technical adapters cho các công nghệ cụ thể
- **Configuration Layer**: Dependency injection và service wiring
- **Resilience Patterns**: Circuit breaker, retry và fault handling
- **Ví dụ Sử dụng**: Real-world service integration patterns

### ⚡ [Hướng dẫn Nhanh](QUICK_REFERENCE.md)
Hướng dẫn nhanh cho developers với các patterns phổ biến, utilities và ví dụ.

**Nội dung:**
- **Core Components**: ApiResponse, Application Services, Domain patterns
- **Utility Classes**: Cách sử dụng StringUtils, DateTimeUtils, PageUtils
- **Command/Query Handlers**: Template implementations với tích hợp service  
- **Exception Handling**: Business exceptions và global error handling
- **Development Commands**: Lệnh build, test và deployment
- **Common Patterns**: Thêm tính năng, sử dụng infrastructure services, xử lý lỗi

### 🏷️ [Tham chiếu Annotations](ANNOTATIONS_REFERENCE.md)
Tài liệu tham chiếu toàn diện về tất cả annotations được sử dụng trong template với ngữ cảnh Clean Architecture.

**Nội dung:**
- **Spring Boot Annotations**: @SpringBootApplication, @Service, @Component, @Configuration
- **Spring MVC Annotations**: @RestController, @RequestMapping, @Valid, @PathVariable
- **JPA/Hibernate Annotations**: @Entity, @Table, @Embedded, @Embeddable
- **Validation Annotations**: @NotBlank, @Email, @Size với các pattern validation
- **Lombok Annotations**: @Data, @Builder, @RequiredArgsConstructor
- **MapStruct Annotations**: @Mapper, @Mapping với tích hợp BaseMapper
- **Testing Annotations**: @SpringBootTest, @WebMvcTest, @DataJpaTest
- **Best Practices**: Cách sử dụng annotation theo từng layer và kết hợp

## 🚀 Bắt đầu Nhanh

### **Yêu cầu hệ thống**
- Java 17+
- Maven 3.6+
- IDE yêu thích (khuyến khích IntelliJ IDEA)

### **Chạy Ứng dụng**
```bash
# Clone và di chuyển đến thư mục dự án
git clone <repository-url>
cd SpringBootTemplate

# Chạy ứng dụng
mvn spring-boot:run

# Ứng dụng sẽ khởi động tại http://localhost:8080
```

### **Kiểm tra APIs**
```bash
# Tạo user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","email":"john.doe@example.com"}'

# Lấy thông tin user
curl http://localhost:8080/api/users/1
```

### **Truy cập Công cụ**
- **H2 Console**: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:testdb`)
- **Ứng dụng**: http://localhost:8080
- **Health Check**: http://localhost:8080/actuator/health

## 🎯 Lợi ích Chính

### **Cho Đội Ngũ Phát Triển**
- ✅ **Cấu trúc Nhất quán**: Tất cả dự án đều tuân theo cùng một pattern Clean Architecture
- ✅ **Components Tái sử dụng**: Common utilities và infrastructure services sẵn sàng sử dụng
- ✅ **Testing Tốt hơn**: Architecture hỗ trợ tất cả levels testing với proper isolation
- ✅ **Phát triển Nhanh hơn**: Pre-built patterns giảm boilerplate code
- ✅ **Onboarding Dễ dàng**: Cấu trúc rõ ràng và tài liệu cho developers mới

### **Ưu điểm Kỹ thuật**
- ✅ **Clean Architecture**: Dependency inversion và layer separation đúng cách
- ✅ **Service Interface Pattern**: Dễ dàng mock, test và swap implementations
- ✅ **Responses Chuẩn hóa**: ApiResponse<T> cho API responses nhất quán
- ✅ **Global Exception Handling**: Xử lý lỗi tập trung với HTTP status codes phù hợp
- ✅ **Infrastructure Abstraction**: Application layer không phụ thuộc vào technical details
- ✅ **Testing Toàn diện**: Architecture tests đảm bảo design principles được duy trì

## 🧩 Core Components Có sẵn

- **ApiResponse<T>**: Standardized response wrapper
- **GlobalExceptionHandler**: Xử lý exception tập trung
- **Application Service Interfaces**: CacheService, ExternalApiService, MessagingService, SecurityService
- **Utility Classes**: StringUtils, DateTimeUtils, PageUtils
- **Base Classes**: BaseEntity, AggregateRoot, ValueObject, BaseMapper
- **Configuration**: UseCaseConfig, WebConfig, LoggingConfig
- **Testing Support**: Architecture tests và layer-specific test configurations

Nếu bạn là người mới:

1. **Đọc [README.md](../README.md)** để setup dự án
2. **Xem [ARCHITECTURE.md](ARCHITECTURE.md)** để hiểu tổng quan
3. **Theo dõi [APPLICATION_FLOW.md](APPLICATION_FLOW.md)** để hiểu luồng hoạt động
4. **Sử dụng [QUICK_REFERENCE.md](QUICK_REFERENCE.md)** làm cheat sheet

## 🎯 Dành Cho Từng Audience

### **Architects & Senior Developers**
- [ARCHITECTURE.md](ARCHITECTURE.md) - Thiết kế tổng thể
- [APPLICATION_FLOW.md](APPLICATION_FLOW.md) - Luồng xử lý chi tiết
- [INFRASTRUCTURE_LAYERS.md](INFRASTRUCTURE_LAYERS.md) - Infrastructure design

### **Developers**
- [QUICK_REFERENCE.md](QUICK_REFERENCE.md) - Development guide
- [APPLICATION_FLOW.md](APPLICATION_FLOW.md) - Understanding flows
- [ARCHITECTURE.md](ARCHITECTURE.md) - Code organization

### **New Team Members**
- [README.md](../README.md) - Setup và bắt đầu
- [QUICK_REFERENCE.md](QUICK_REFERENCE.md) - Common patterns
- [APPLICATION_FLOW.md](APPLICATION_FLOW.md) - How things work

## 📋 Các Khái niệm Chính Được Đề cập

### Nguyên tắc Clean Architecture
- **Dependency Inversion** - Dependencies hướng vào trong
- **Layer Separation** - Ranh giới rõ ràng giữa các layers
- **Business Logic Isolation** - Core logic độc lập với external concerns

### Domain-Driven Design (DDD)
- **Aggregates** - Consistency boundaries
- **Entities** - Objects có identity
- **Value Objects** - Immutable objects không có identity
- **Domain Events** - Events có ý nghĩa business
- **Repositories** - Data access abstraction

### CQRS Pattern
- **Commands** - Operations thay đổi state
- **Queries** - Operations đọc dữ liệu
- **Handlers** - Processing logic cho commands/queries
- **Dispatcher** - Routes commands/queries đến handlers

### Infrastructure Patterns
- **Adapter Pattern** - Tích hợp external system
- **Repository Pattern** - Data access abstraction
- **Event Sourcing** - Domain event handling
- **Circuit Breaker** - Resilience cho external calls

## 🔍 Tìm Kiếm Nhanh

Sử dụng Ctrl+F để tìm kiếm các từ khóa:

- **Command** → [APPLICATION_FLOW.md](APPLICATION_FLOW.md) + [QUICK_REFERENCE.md](QUICK_REFERENCE.md)
- **Query** → [APPLICATION_FLOW.md](APPLICATION_FLOW.md) + [QUICK_REFERENCE.md](QUICK_REFERENCE.md)
- **Domain Event** → [APPLICATION_FLOW.md](APPLICATION_FLOW.md) + [QUICK_REFERENCE.md](QUICK_REFERENCE.md)
- **Repository** → [ARCHITECTURE.md](ARCHITECTURE.md) + [INFRASTRUCTURE_LAYERS.md](INFRASTRUCTURE_LAYERS.md)
- **Validation** → [QUICK_REFERENCE.md](QUICK_REFERENCE.md)
- **Exception Handling** → [APPLICATION_FLOW.md](APPLICATION_FLOW.md) + [QUICK_REFERENCE.md](QUICK_REFERENCE.md)
- **Testing** → [QUICK_REFERENCE.md](QUICK_REFERENCE.md)
- **Cache** → [INFRASTRUCTURE_LAYERS.md](INFRASTRUCTURE_LAYERS.md)
- **Security** → [INFRASTRUCTURE_LAYERS.md](INFRASTRUCTURE_LAYERS.md)

## 📞 Hỗ Trợ

Nếu bạn có thắc mắc:

1. Kiểm tra các tài liệu trên
2. Xem examples trong code
3. Tạo issue trên GitHub repository
4. Liên hệ team lead

---

**Happy Coding! 🎉**
