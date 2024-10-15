package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Repo extending JpaRepository makes it that we can access common database operations
    // such as: saving and deleting, without the need to write query

    // You can add custom query methods here if needed
}
