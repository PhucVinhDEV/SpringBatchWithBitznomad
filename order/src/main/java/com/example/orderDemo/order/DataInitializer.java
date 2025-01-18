package com.example.orderDemo.order;

import com.example.orderDemo.order.model.Product;
import com.example.orderDemo.order.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    private final ProductRepository productRepository;

    public DataInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Lưu danh sách sản phẩm vào cơ sở dữ liệu
        productRepository.saveAll(WriteFile.FAKE_PRODUCTS);

        System.out.println("Đã lưu " + WriteFile.FAKE_PRODUCTS.size() + " sản phẩm vào cơ sở dữ liệu.");
    }
}
