package com.example.orderDemo.order.processor;

import com.example.orderDemo.order.model.Order;
import com.example.orderDemo.order.model.Product;
import com.example.orderDemo.order.model.RevenueSummary;
import com.example.orderDemo.order.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class totalRevenueProcessor implements ItemProcessor<Order, RevenueSummary> {

    private final ProductRepository productRepository;

    @Override
    public RevenueSummary process(Order order) throws Exception {
        Product product = productRepository.findById(order.getProductId())
                .orElse(Product.builder()
                        .productId(order.getProductId())
                        .category("unknown")
                        .build());

        double totalRevenue = order.getQuantity() * order.getPrice();

        RevenueSummary revenueSummary = new RevenueSummary();
        revenueSummary.setCategoryName(product.getCategory());
        revenueSummary.setRevenue(totalRevenue);

        return revenueSummary;
    }
}
