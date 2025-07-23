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
└── demo/                          # Demo package - Example implementation
    ├── domain/                     # Demo-specific domain logic
    ├── application/                # Demo application services
    ├── infrastructure/             # Demo infrastructure
    └── presentation/               # Demo REST controllers
```

## Tính năng chính

- **Clean Architecture**: Dependency inversion và layer separation đúng cách
- **DDD Implementation**: Aggregates, entities, value objects và domain services
- **CQRS Pattern**: Tách biệt Command và Query để scalability tốt hơn
- **Event-Driven Architecture**: Domain events và event handlers
- **Comprehensive Testing**: Unit, integration và architecture tests
- **Developer-Friendly**: Cấu trúc rõ ràng và code được document tốt

## Bắt đầu

### Yêu cầu

- Java 17 trở lên
- Maven 3.8+
- IDE yêu thích (khuyên dùng IntelliJ IDEA)

### Chạy ứng dụng

```bash
mvn spring-boot:run
```

### Chạy tests

```bash
mvn test
```

### API Documentation

Sau khi khởi động ứng dụng, truy cập:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Quy tắc kiến trúc

Dự án này bao gồm ArchUnit tests để đảm bảo:

- Dependencies hướng vào trong (quy tắc Clean Architecture)
- Domain layer không có external dependencies
- Infrastructure phụ thuộc vào domain interfaces
- Duy trì cấu trúc package đúng cách
