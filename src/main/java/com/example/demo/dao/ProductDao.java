package com.example.demo.dao;

import com.example.demo.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDao {

    private final EntityManager entityManager;

    @Transactional
    public Product createProduct (Product product) {
        entityManager.persist(product);
        return product;
    }

    public Product getProduct (Long id) {
        return entityManager.find(Product.class, id);
    }
}
