package com.example.spring.model;

public class Product {
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;

    public Product() {
    }

    public Product(Long id, String name, Double price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(String name, Double price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}

/*
========================================================================================
GIẢI THÍCH FILE Product.java:
1. Bản chất của lớp Model (POJO - Plain Old Java Object):
   - Đây là đối tượng biểu diễn dữ liệu bảng "products" trong cơ sở dữ liệu.
   - Bao gồm các trường tương ứng với các cột trong DB: id, name, price, quantity.
   - Chứa các constructor, getter, setter và toString để thuận tiện cho việc ánh xạ dữ liệu
     và in ra màn hình khi truy vấn qua JdbcTemplate.
========================================================================================
*/
