# Spring Boot Clean Architecture Template

Một template ứng dụng Spring Boot hiện đại tuân theo các nguyên tắc Clean Architecture và Domain-Driven Design (DDD).

## Tổng quan kiến trúc

Dự án này triển khai Clean Architecture với các khái niệm DDD, cung cấp:

- **Clean Architecture Layers**: Tách biệt rõ ràng giữa các tầng Domain, Application, Infrastructure và Presentation
- **Domain-Driven Design**: Rich domain models, aggregates, value objects và domain services
- **CQRS Pattern**: Tách biệt trách nhiệm Command và Query
- **Hexagonal Architecture**: Ports và adapters pattern cho các dependencies bên ngoài

## Cấu trúc dự án

```
src/main/java/com/example/
├── core/                           # Core package - Các thành phần tái sử dụng
│   ├── common/                     # Utilities chung và shared components
│   ├── domain/                     # Domain layer - Business logic
│   ├── application/                # Application layer - Use cases
│   └── infrastructure/             # Infrastructure layer - External concerns
│       ├── database/               # Database adapters và repositories
│       ├── cache/                  # Cache adapters
│       ├── external/               # External API adapters với resilience
│       ├── messaging/              # Event publishing và messaging
│       └── security/               # Security configuration
└── demo/                          # Demo package - Example implementation
    ├── domain/                     # Demo-specific domain logic
    ├── application/                # Demo application services
    ├── infrastructure/             # Demo infrastructure adapters
    │   └── database/               # Demo database implementations
    └── presentation/               # Demo REST controllers
```

## Infrastructure Layers

Infrastructure layer được phân tách thành các sub-layers với mục đích đặc thù:

- **Database Layer**: Quản lý truy cập cơ sở dữ liệu với JPA/Hibernate
- **Cache Layer**: Adapter cho caching mechanisms
- **External API Layer**: Tích hợp với external services, bao gồm resilience patterns (Circuit Breaker, Retry, Time Limiter)
- **Messaging Layer**: Domain event publishing và message handling
- **Security Layer**: Authentication, authorization và security policies

## Tính năng chính

- **Clean Architecture**: Dependency inversion và layer separation đúng cách
- **DDD Implementation**: Aggregates, entities, value objects và domain services
- **CQRS Pattern**: Tách biệt Command và Query để scalability tốt hơn
- **Event-Driven Architecture**: Domain events và event handlers
- **Resilience Patterns**: Circuit Breaker, Retry, Time Limiter với Resilience4j
- **Layered Infrastructure**: Database, Cache, External API, Messaging, Security layers
- **Comprehensive Testing**: Unit, integration và architecture tests
- **Developer-Friendly**: Cấu trúc rõ ràng và code được document tốt
- **Production Ready**: Security, validation, error handling và monitoring

## Bắt đầu

### Quick Start

```bash
# 1. Clone repository
git clone https://github.com/ethan-le-grapeseed-com/SpringBootTemplate.git
cd SpringBootTemplate

# 2. Chạy ứng dụng (sử dụng Maven Wrapper - không cần cài Maven)
./mvnw.cmd spring-boot:run  # Windows
./mvnw spring-boot:run      # Unix/Linux/macOS

# 3. Truy cập ứng dụng
# http://localhost:8080/api/users/1
```

### Yêu cầu

- Java 17 trở lên
- Maven 3.8+
- IDE yêu thích (khuyên dùng IntelliJ IDEA)

### Cài đặt Java

#### Java Development Kit (JDK) 17 hoặc mới hơn:

**Windows:**
1. Tải Oracle JDK hoặc OpenJDK từ:
   - Oracle JDK: https://www.oracle.com/java/technologies/downloads/
   - OpenJDK: https://adoptium.net/ (khuyên dùng)
2. Chạy file installer và làm theo hướng dẫn
3. Thiết lập biến môi trường:
   - Thêm `JAVA_HOME` vào Environment Variables (ví dụ: `C:\Program Files\Eclipse Adoptium\jdk-17.0.x`)
   - Thêm `%JAVA_HOME%\bin` vào PATH
4. Kiểm tra cài đặt: `java --version`

**macOS:**
```bash
# Sử dụng Homebrew (khuyên dùng)
brew install openjdk@17

# Hoặc sử dụng SDKMAN
curl -s "https://get.sdkman.io" | bash
sdk install java 17.0.x-tem
```

**Linux (Ubuntu/Debian):**
```bash
# OpenJDK 17
sudo apt update
sudo apt install openjdk-17-jdk

# Kiểm tra cài đặt
java --version
javac --version
```

**Kiểm tra version Java:**
```bash
java --version
javac --version
```

### Cài đặt Maven

#### Windows:
1. Tải Maven từ https://maven.apache.org/download.cgi
2. Giải nén vào thư mục (ví dụ: `C:\Program Files\Apache\maven`)
3. Thêm `MAVEN_HOME` vào Environment Variables: `C:\Program Files\Apache\maven`
4. Thêm `%MAVEN_HOME%\bin` vào PATH
5. Kiểm tra cài đặt: `mvn --version`

#### macOS (với Homebrew):
```bash
brew install maven
```

#### Linux (Ubuntu/Debian):
```bash
sudo apt update
sudo apt install maven
```

#### Hoặc sử dụng Maven Wrapper (đã có sẵn trong project):
```bash
# Windows
./mvnw.cmd --version

# Unix/Linux/macOS
./mvnw --version
```

### Troubleshooting

#### Lỗi thường gặp:

**Java không được nhận diện:**
```bash
# Windows
echo %JAVA_HOME%
echo %PATH%

# Unix/Linux/macOS  
echo $JAVA_HOME
echo $PATH
```

**Maven không hoạt động:**
- Đảm bảo JAVA_HOME được thiết lập đúng
- Kiểm tra PATH có chứa Maven bin directory
- Restart terminal/command prompt sau khi thiết lập biến môi trường

**Version Java không đúng:**
```bash
# Kiểm tra Java version hiện tại
java --version

# Nếu có nhiều version Java, có thể cần update JAVA_HOME
```

### IDE Setup (Tùy chọn)

#### IntelliJ IDEA (Khuyên dùng):
1. Tải từ: https://www.jetbrains.com/idea/
2. Import project: File → Open → Chọn thư mục dự án
3. IDE sẽ tự động detect Maven project và download dependencies
4. Đảm bảo Project SDK được set đúng Java 17+

#### Visual Studio Code:
1. Cài đặt Extension Pack for Java
2. Mở thư mục project
3. VS Code sẽ tự động setup Java project

#### Eclipse:
1. File → Import → Existing Maven Projects
2. Chọn thư mục dự án
3. Eclipse sẽ import và setup project

### Chạy ứng dụng

Sử dụng Maven:
```bash
mvn spring-boot:run
```

Hoặc sử dụng Maven Wrapper (khuyên dùng):
```bash
# Windows
./mvnw.cmd spring-boot:run

# Unix/Linux/macOS
./mvnw spring-boot:run
```

### Chạy tests

Sử dụng Maven:
```bash
mvn test
```

Hoặc sử dụng Maven Wrapper:
```bash
# Windows
./mvnw.cmd test

# Unix/Linux/macOS
./mvnw test
```

### API Documentation

Sau khi khởi động ứng dụng, truy cập:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- H2 Database Console: http://localhost:8080/h2-console (username: `sa`, password: để trống)

#### API Endpoints có sẵn:
- **POST /api/users** - Tạo user mới
- **GET /api/users/{id}** - Lấy thông tin user theo ID
- **GET /actuator/health** - Health check

#### Ví dụ sử dụng API:

Tạo user mới:
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","email":"john.doe@example.com"}'
```

Lấy thông tin user:
```bash
curl -X GET http://localhost:8080/api/users/1
```

## Quy tắc kiến trúc

Dự án này bao gồm ArchUnit tests để đảm bảo:

- Dependencies hướng vào trong (quy tắc Clean Architecture)
- Domain layer không có external dependencies
- Infrastructure phụ thuộc vào domain interfaces
- Duy trì cấu trúc package đúng cách
