package com.example.spring;

import com.example.spring.config.AppConfig;
import com.example.spring.model.Product;
import com.example.spring.repository.ProductRepository;
import com.example.spring.service.ProductService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("1. KHOI DONG SPRING IOC CONTAINER");
        System.out.println("==========================================================");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("\n==========================================================");
        System.out.println("2. KHOI TAO CSDL VOI JDBCTEMPLATE");
        System.out.println("==========================================================");

        ProductRepository repository = context.getBean(ProductRepository.class);
        repository.initDatabase();
        System.out.println("Bang 'products' da duoc khoi tao thanh cong trong CSDL H2 in-memory!");

        System.out.println("\n==========================================================");
        System.out.println("3. LAY BEAN PRODUCTSERVICE VA THUC THI NGHEIP VU (CO AOP)");
        System.out.println("==========================================================");

        ProductService productService = context.getBean(ProductService.class);

        System.out.println("\n--- [DEMO 1: THEM SAN PHAM] ---");
        productService.addProduct("Laptop Dell XPS", 1500.0, 10);
        productService.addProduct("MacBook Pro M3", 2200.0, 5);
        productService.addProduct("Ban phim co Keychron", 120.0, 25);

        System.out.println("\n--- [DEMO 2: TRUY VAN TAT CA SAN PHAM] ---");
        List<Product> products = productService.getAllProducts();
        System.out.println("Danh sach san pham hien tai:");
        for (Product p : products) {
            System.out.println("  -> " + p);
        }

        System.out.println("\n--- [DEMO 3: TRUY VAN CHI TIET SAN PHAM THEO ID] ---");
        Product product = productService.getProductById(1L);
        System.out.println("Chi tiet san pham ID = 1: " + product);

        System.out.println("\n--- [DEMO 4: CAP NHAT GIA SAN PHAM] ---");
        productService.updatePrice(1L, 1450.0);
        System.out.println("Sau khi giam gia ID = 1: " + productService.getProductById(1L));

        System.out.println("\n--- [DEMO 5: XOA SAN PHAM] ---");
        productService.deleteProduct(3L);
        System.out.println("Danh sach san pham sau khi xoa ID = 3:");
        for (Product p : productService.getAllProducts()) {
            System.out.println("  -> " + p);
        }

        System.out.println("\n==========================================================");
        System.out.println("4. DONG SPRING IOC CONTAINER");
        System.out.println("==========================================================");

        context.close();

        System.out.println("Chuong trinh ket thuc!");
    }
}

/*
========================================================================================
GIẢI THÍCH FILE Main.java:
1. Toàn bộ quy trình hoạt động của dự án thực hành kết hợp 4 chuyên đề:
   a. Spring IoC Container (AnnotationConfigApplicationContext):
      - Khi khởi tạo context với AppConfig.class, Spring đọc cấu hình, quét các Bean (@Component,
        @Service, @Repository, @Aspect) và khởi tạo chúng vào trong Container.
      - Bạn quan sát trên màn hình sẽ thấy log của @PostConstruct từ ProductService được gọi tự động.

   b. Dependency Injection (DI):
      - Khi context.getBean(ProductService.class) được gọi, ProductService đã có sẵn một đối tượng
        ProductRepository bên trong mà không cần bất kỳ dòng code "new ProductRepository()" nào.
      - ProductRepository cũng đã được inject sẵn bean JdbcTemplate.

   c. AOP APIs (LoggingAspect):
      - Mỗi lần bất kỳ phương thức nào của ProductService được gọi (addProduct, getAllProducts, v.v.),
        Spring AOP tự động chặn lại (intercept):
        + In log trước khi chạy (@Before: in tên hàm và danh sách tham số).
        + Đo thời gian thực thi (@Around: đo mili giây bằng ProceedingJoinPoint).
        + In log kết quả trả về khi kết thúc (@AfterReturning).

   d. Data access with JDBC (JdbcTemplate):
      - Tất cả các thao tác DDL tạo bảng, INSERT thêm mới, SELECT truy vấn danh sách/chi tiết,
        UPDATE cập nhật giá và DELETE xóa dữ liệu đều chạy thông qua JdbcTemplate và H2 database.

   e. Đóng Container (context.close()):
      - Khi đóng context, Spring kích hoạt hook @PreDestroy trên ProductService để dọn dẹp tài nguyên
        trước khi toàn bộ ứng dụng dừng lại.
========================================================================================
*/
