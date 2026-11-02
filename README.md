# Dự Án Thực Hành Củng Cố Kiến Thức Spring Framework

Dự án này được thiết kế dựa trên các chuyên đề:
1. **Dependency Injection & IoC Container**
2. **Spring Bean & Bean Lifecycle**
3. **AOP APIs (Lập trình hướng khía cạnh)**
4. **Data access with JDBC (JdbcTemplate & H2 In-Memory Database)**
5. **Maven Project Management**

---

## 1. Cấu Trúc Thư Mục

```
sping_tutorial/
├── pom.xml                                      # Quản lý dependencies & build với Maven
├── run.bat                                      # Script chạy 1-click trên Windows Command Prompt
├── run.ps1                                      # Script chạy trên PowerShell
└── src/
    └── main/
        └── java/
            └── com/example/spring/
                ├── Main.java                    # Điểm khởi chạy IoC Container & thực thi các chức năng
                ├── config/
                │   └── AppConfig.java           # Lớp cấu hình IoC (@Configuration, @ComponentScan, @Bean)
                ├── model/
                │   └── Product.java             # POJO Model ánh xạ bảng dữ liệu products
                ├── repository/
                │   └── ProductRepository.java   # Tầng dữ liệu sử dụng JdbcTemplate & RowMapper
                ├── service/
                │   └── ProductService.java      # Tầng nghiệp vụ (Dependency Injection & Bean Lifecycle)
                └── aspect/
                    └── LoggingAspect.java       # Aspect xử lý AOP (@Before, @AfterReturning, @Around)
```

---

## 2. Cách Chạy Ứng Dụng

### Cách 1: Chạy bằng file script có sẵn
- Mở PowerShell trong thư mục này và gõ:
  ```powershell
  .\run.ps1
  ```
- Hoặc nhấp đúp chuột vào file `run.bat`.

### Cách 2: Mở trong IntelliJ IDEA
1. Mở IntelliJ IDEA -> Chọn **Open** -> Chọn thư mục `sping_tutorial`.
2. IntelliJ sẽ tự động nhận diện dự án Maven.
3. Mở file `src/main/java/com/example/spring/Main.java` và bấm nút **Run** (biểu tượng tam giác xanh).

---

## 3. Bản Đồ Khái Niệm Trong Dự Án

| Chuyên Đề | File Thể Hiện | Chi Tiết Thực Hiện |
| :--- | :--- | :--- |
| **IoC Container** | `AppConfig.java`, `Main.java` | Sử dụng `AnnotationConfigApplicationContext` làm container quản lý đối tượng. |
| **Dependency Injection** | `ProductService.java`, `ProductRepository.java` | Tiêm dependencies qua Constructor (`@Autowired`), không dùng toán tử `new`. |
| **Spring Bean** | `AppConfig.java`, `ProductService.java`, `ProductRepository.java` | Khởi tạo bean bằng `@Component`, `@Service`, `@Repository` và `@Bean`. Trình diễn vòng đời với `@PostConstruct` và `@PreDestroy`. |
| **AOP APIs** | `LoggingAspect.java`, `AppConfig.java` | Kích hoạt với `@EnableAspectJAutoProxy`. Cắt ngang phương thức với `@Before`, `@AfterReturning`, `@Around` (đo mili giây chạy). |
| **Data access with JDBC** | `ProductRepository.java`, `AppConfig.java` | Cấu hình `DataSource` (H2 in-memory), `JdbcTemplate` thực hiện CRUD và ánh xạ kết quả với `RowMapper`. |


