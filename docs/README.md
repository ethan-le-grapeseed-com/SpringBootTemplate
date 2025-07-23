# Tài Liệu Dự Án

Chào mừng bạn đến với tài liệu của Spring Boot Clean Architecture Template. Dưới đây là danh sách các tài liệu có sẵn:

## 📚 Tài Liệu Chi Tiết

### 🏗️ [Kiến Trúc Hệ Thống](ARCHITECTURE.md)
Mô tả chi tiết về cấu trúc dự án, các layers và nguyên tắc thiết kế Clean Architecture.

**Nội dung:**
- Cấu trúc thư mục chi tiết
- Domain layer với Entities và Value Objects
- Application layer với Use Cases
- Infrastructure layer với Adapters
- Presentation layer với Controllers

### 🔄 [Luồng Hoạt Động Ứng Dụng](APPLICATION_FLOW.md)
Mô tả chi tiết luồng xử lý từ HTTP request đến response, bao gồm Command và Query flows.

**Nội dung:**
- Command Flow (Create/Update)
- Query Flow (Read)
- Event Processing Flow
- Error Handling Flow
- Configuration và Startup
- Performance Considerations

### 🛠️ [Infrastructure Layers](INFRASTRUCTURE_LAYERS.md)
Chi tiết về các tầng infrastructure và cách sử dụng các adapters.

**Nội dung:**
- Database Layer
- Cache Layer
- External API Layer
- Messaging Layer
- Security Layer
- Usage Examples

### ⚡ [Quick Reference Guide](QUICK_REFERENCE.md)
Hướng dẫn nhanh cho developers với các patterns thường dùng và examples.

**Nội dung:**
- API Endpoints
- Key Components
- Development Workflow
- Common Patterns
- Useful Commands
- Monitoring & Debug

## 🚀 Bắt Đầu Nhanh

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

## 📋 Key Concepts Được Đề Cập

### Clean Architecture Principles
- **Dependency Inversion** - Dependencies point inward
- **Layer Separation** - Clear boundaries between layers
- **Business Logic Isolation** - Core logic independent of external concerns

### Domain-Driven Design (DDD)
- **Aggregates** - Consistency boundaries
- **Entities** - Objects with identity
- **Value Objects** - Immutable objects without identity
- **Domain Events** - Business-significant events
- **Repositories** - Data access abstraction

### CQRS Pattern
- **Commands** - Operations that change state
- **Queries** - Operations that read data
- **Handlers** - Processing logic for commands/queries
- **Dispatcher** - Routes commands/queries to handlers

### Infrastructure Patterns
- **Adapter Pattern** - External system integration
- **Repository Pattern** - Data access abstraction
- **Event Sourcing** - Domain event handling
- **Circuit Breaker** - Resilience for external calls

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
