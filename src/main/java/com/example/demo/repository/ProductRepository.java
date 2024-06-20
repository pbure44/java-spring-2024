package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByPriceGreaterThanEqual(Double price);

    List<Product> findAllByPriceLessThanEqual(Double price);

    @Query(value = "select * from products where price <= :price", nativeQuery = true)
    List<Product> findAllByPriceLessThanEqualNative(Double price);

    List<Product> findAllByPriceBetween(Double minPrice, Double maxPrice);
}
