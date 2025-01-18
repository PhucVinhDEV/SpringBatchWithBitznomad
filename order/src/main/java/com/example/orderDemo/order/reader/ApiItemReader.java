package com.example.orderDemo.order.reader;

import com.example.orderDemo.order.model.Order;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Component
public class ApiItemReader implements ItemReader<Order> {
    private List<Order> orders;
    private int nextOrderIndex;

//    public ApiItemReader(String apiUrl) {
//        this.orders = fetchOrdersFromApi(apiUrl);
//        this.nextOrderIndex = 0;
//    }

    @Override
    public Order read() {
        if (nextOrderIndex < orders.size()) {
            return orders.get(nextOrderIndex++);
        }
        return null;
    }


    private List<Order> fetchOrdersFromApi(String apiUrl) {
        // Sử dụng RestTemplate hoặc WebClient để gọi API
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrl, List.class);
    }
}
