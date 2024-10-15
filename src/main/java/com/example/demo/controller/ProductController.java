package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        System.out.println("GOES THROUGH CONTROLLER");
        if (product.getCategoryId() == 0) { //no longer necessary because we check in app.js
            product.setCategoryId(1);
        }
        if (product.getBrandId() == 0) {
            product.setBrandId(1);
        }
        if (product.getPortion() == null || product.getPortion().isEmpty()) {
            product.setPortion("Default Portion");
        }
        if (product.getImage() == null || product.getImage().isEmpty()) {
            product.setImage("default-image-url");
        }

        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setDeleted(false);

        return productService.save(product);
    }


    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
        return productService.findById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
        Product existingProduct = productService.findById(id);
        if (existingProduct != null) {
            product.setCreatedAt(existingProduct.getCreatedAt());
            product.setUpdatedAt(LocalDateTime.now());
            return productService.update(id, product);
        } else {
            throw new RuntimeException("Product not found");
        }
    }


    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        productService.delete(id);
    }
}
