package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.ProductRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    //Method to test the database connection, not in use now
    public void saveSampleProduct() {
        Product product = new Product();
        product.setName("Test");
        product.setImage("image.jpg");
        product.setCategoryId(2);
        product.setBrandId(2);
        product.setPortion("2 kg");
        product.setPrice(15.50);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setDeleted(false);

        productRepository.save(product);
        System.out.println("Sample product saved successfully!");
    }

    // Create a new product
    public Product save(Product product) {

        return productRepository.save(product);
    }

    // Retrieve all products
    public List<Product> findAll() {

        return productRepository.findAll();
    }

    // Retrieve a product by ID
    public Product findById(int id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null); // return null or throw an exception if not found
    }

    // Update a product
    public Product update(int id, Product product) {
        // Check if the product exists
        if (productRepository.existsById(id)) {
            product.setId(id); // Set the ID to ensure we're updating the correct product
            return productRepository.save(product);
        }
        return null; // return null or throw an exception if not found
    }

    // Delete a product
    public void delete(int id) {
        productRepository.deleteById(id);
    }
}
