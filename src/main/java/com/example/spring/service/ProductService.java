package com.example.spring.service;

import com.example.spring.model.Product;
import com.example.spring.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void onInit() {
        System.out.println("[BEAN LIFECYCLE - @PostConstruct] ProductService bean da duoc khoi tao va san sang su dung!");
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("[BEAN LIFECYCLE - @PreDestroy] ProductService bean dang duoc don dep truoc khi IoC Container dong lai!");
    }

    public void addProduct(String name, Double price, Integer quantity) {
        Product product = new Product(name, price, quantity);
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void updatePrice(Long id, Double newPrice) {
        productRepository.updatePrice(id, newPrice);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

/*
========================================================================================
GIẢI THÍCH FILE ProductService.java:
1. Dependency Injection (DI) & IoC Container (Mục 1 trong hình ảnh):
   - Thay vì ProductService phải tự khởi tạo: "this.productRepository = new ProductRepository(...)",
     chúng ta để Spring IoC Container làm nhiệm vụ tạo và tiêm (inject) ProductRepository vào thông qua Constructor:
     @Autowired
     public ProductService(ProductRepository productRepository) { ... }
   - Đây là kỹ thuật Inversion of Control (IoC): quyền kiểm soát việc tạo lập đối tượng được đảo ngược,
     chuyển giao từ lập trình viên sang cho Spring Container quản lý.
   - Constructor Injection giúp code loose coupling (giảm phụ thuộc trực tiếp), dễ dàng viết Unit Test (mock repository)
     và đảm bảo tính bất biến (immutable) với từ khóa final.

2. Spring Bean & Vòng đời Bean (Bean Lifecycle - Mục 2 trong hình ảnh):
   - @Service: Đánh dấu class này là tầng Service (xử lý nghiệp vụ), Spring sẽ quét và quản lý nó như một Spring Bean.
   - Mặc định phạm vi (scope) của Bean là Singleton: chỉ có duy nhất 1 instance trong suốt vòng đời ApplicationContext.
   - @PostConstruct: Phương thức này được Spring tự động gọi ngay sau khi Bean được khởi tạo và tiêm đầy đủ dependencies.
   - @PreDestroy: Phương thức này được Spring gọi ngay trước khi Bean bị hủy và giải phóng khi ApplicationContext đóng lại.
========================================================================================
*/
