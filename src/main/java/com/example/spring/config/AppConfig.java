package com.example.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.example.spring")
@EnableAspectJAutoProxy
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:productdb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}

/*
========================================================================================
GIẢI THÍCH FILE AppConfig.java:
1. IoC Container & Cấu hình bằng Java Config (Mục 1 & 2 trong hình ảnh):
   - @Configuration: Đánh dấu đây là lớp cấu hình trung tâm cho Spring IoC Container (thay thế file XML cũ).
   - @ComponentScan: Yêu cầu Spring tự động quét package "com.example.spring" và các package con
     để tìm các class có đánh dấu @Component, @Repository, @Service, @Aspect và tự động đăng ký chúng thành Spring Bean.

2. Định nghĩa Spring Bean bằng @Bean:
   - Trong khi @Component/@Service dùng cho các class do chính chúng ta viết mã nguồn,
     @Bean được dùng trong các phương thức của lớp @Configuration để tạo và cấu hình các đối tượng từ thư viện bên thứ 3
     (ở đây là DataSource và JdbcTemplate của Spring/H2).
   - @Bean dataSource(): Khởi tạo kết nối đến CSDL H2 in-memory (lưu trữ trong RAM khi chạy, tự động giải phóng khi tắt).
   - @Bean jdbcTemplate(DataSource dataSource): Spring tự động tiêm bean dataSource ở trên vào tham số của hàm này
     để tạo ra bean JdbcTemplate duy nhất dùng xuyên suốt ứng dụng.

3. Kích hoạt AOP:
   - @EnableAspectJAutoProxy: Bật tính năng tự động tạo proxy của Spring AOP, giúp các Aspect như LoggingAspect
     có thể bọc và can thiệp vào các Spring Bean mục tiêu.
========================================================================================
*/
