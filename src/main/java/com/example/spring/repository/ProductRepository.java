package com.example.spring.repository;

import com.example.spring.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void initDatabase() {
        String dropSql = "DROP TABLE IF EXISTS products";
        String createSql = "CREATE TABLE products (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "price DOUBLE NOT NULL, " +
                "quantity INT NOT NULL" +
                ")";
        jdbcTemplate.execute(dropSql);
        jdbcTemplate.execute(createSql);
    }

    public void save(Product product) {
        String sql = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getQuantity());
    }

    public List<Product> findAll() {
        String sql = "SELECT id, name, price, quantity FROM products";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    public Product findById(Long id) {
        String sql = "SELECT id, name, price, quantity FROM products WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new ProductRowMapper(), id);
    }

    public void updatePrice(Long id, Double newPrice) {
        String sql = "UPDATE products SET price = ? WHERE id = ?";
        jdbcTemplate.update(sql, newPrice, id);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getDouble("price"));
            product.setQuantity(rs.getInt("quantity"));
            return product;
        }
    }
}

/*
========================================================================================
GIẢI THÍCH FILE ProductRepository.java:
1. Data access with JDBC (JdbcTemplate - Mục 4 trong hình ảnh):
   - JdbcTemplate là class cốt lõi của Spring JDBC giúp đơn giản hóa việc thao tác với CSDL.
   - Nó tự động quản lý kết nối (Connection), tạo Statement/PreparedStatement, đóng tài nguyên
     và chuyển đổi các ngoại lệ SQL thuần (SQLException) sang DataAccessException của Spring.
   - jdbcTemplate.execute(): Dùng để thực thi các câu lệnh DDL như CREATE TABLE, DROP TABLE.
   - jdbcTemplate.update(): Dùng để thực thi các câu lệnh thay đổi dữ liệu (INSERT, UPDATE, DELETE).
   - jdbcTemplate.query(): Thực thi truy vấn SELECT trả về danh sách (List<Product>).
   - jdbcTemplate.queryForObject(): Thực thi truy vấn SELECT trả về đúng 1 đối tượng duy nhất.

2. RowMapper:
   - Interface ánh xạ từng dòng kết quả trong ResultSet thành một Object Java (Product).
   - Tái sử dụng được cho cả hàm findAll() và findById().

3. Spring Bean & Dependency Injection (Mục 1 & 2 trong hình ảnh):
   - Annotation @Repository: Đánh dấu class này là tầng truy cập dữ liệu (DAO). Spring IoC Container
     sẽ tự động phát hiện và khởi tạo class này thành một Spring Bean.
   - Constructor Injection (@Autowired): Inject đối tượng JdbcTemplate (được cấu hình trong AppConfig)
     vào Repository này một cách an toàn và dễ kiểm thử.
========================================================================================
*/
