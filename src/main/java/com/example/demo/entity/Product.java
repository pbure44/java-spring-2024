package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private String description;
    @Enumerated(EnumType.STRING)
    private ProductAvailability availability;
    private String category;
    private String owner;
}
