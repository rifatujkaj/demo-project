package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.demo.service.ProductService;

import java.awt.*;
import java.net.URI;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private ProductService productService; // Autowire your ProductService

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("hello");
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Attempting to open browser...");
		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().browse(new URI("http://localhost:8080"));
			System.out.println("Browser should now open.");
		} else {
			System.out.println("Desktop is not supported on this environment.");
		}
	}

}
