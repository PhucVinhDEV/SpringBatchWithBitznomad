package com.example.orderDemo.order;

import com.example.orderDemo.order.model.Order;
import com.example.orderDemo.order.model.Product;
import com.example.orderDemo.order.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Component
public class WriteFile {
    private final ProductRepository productRepository;


    /**
     * Tạo danh sách đơn hàng giả trong khoảng từ start đến end.
     *
     * @param start ID bắt đầu.
     * @param end   ID kết thúc.
     * @return Danh sách đơn hàng.
     */
    public static List<Order> generateFakeOrders(long start, long end) {
        List<Order> orders = new ArrayList<>();
        Random random = new Random();

        for (long i = start; i <= end; i++) {
            String orderId = "ORD" + i; // ID đơn hàng

            // Chọn ngẫu nhiên một sản phẩm từ danh sách FAKE_PRODUCTS
            Product randomProduct = FAKE_PRODUCTS.get(random.nextInt(FAKE_PRODUCTS.size()));
            String productId = randomProduct.getProductId(); // Lấy productId từ sản phẩm ngẫu nhiên // ID sản phẩm ngẫu nhiên từ 1 đến 10
            int quantity = random.nextInt(10) + 1; // Số lượng ngẫu nhiên từ 1 đến 10
            double price = 10 + (100 - 10) * random.nextDouble(); // Giá ngẫu nhiên từ 10 đến 100

            Order order = new Order(orderId, productId, quantity, price);
            orders.add(order);
        }

        return orders;
    }

    /**
     * Ghi danh sách đơn hàng vào file CSV.
     *
     * @param orders Danh sách đơn hàng.
     * @param writer BufferedWriter để ghi dữ liệu.
     */
    public static void writeOrdersToCsv(List<Order> orders, BufferedWriter writer) throws IOException {
        for (Order order : orders) {
            writer.write(
                    order.getOrderId() + "," +
                            order.getProductId() + ","+
                            order.getQuantity() + "," +
                            order.getPrice() + "\n"
            );
        }
    }


    // Danh sách các sản phẩm (Product) giả
    public static final List<Product> FAKE_PRODUCTS = List.of(
            new Product("PROD1", "Laptop", "CAT1"),
            new Product("PROD2", "Smartphone", "CAT1"),
            new Product("PROD3", "T-Shirt", "CAT2"),
            new Product("PROD4", "Jeans", "CAT2"),
            new Product("PROD5", "Blender", "CAT3"),
            new Product("PROD6", "Cookware Set", "CAT3"),
            new Product("PROD7", "Novel", "CAT4"),
            new Product("PROD8", "Textbook", "CAT4"),
            new Product("PROD9", "Action Figure", "CAT5"),
            new Product("PROD10", "Board Game", "CAT5")
    );

    public static void turnOnWritedInput(long totalRow, long chunksize){
        String outputFilePath = "src/main/resources/orders1.csv"; // Đường dẫn file CSV đầu ra

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            // Ghi header
            writer.write("orderId,productId,quantity,price\n");

            // Tạo và ghi dữ liệu theo từng phần
            for (long i = 1; i <= totalRow; i += chunksize) {
                List<Order> orders = generateFakeOrders(i, Math.min(i + chunksize - 1, totalRow));
                writeOrdersToCsv(orders, writer);
                System.out.println("Đã ghi " + i + " đến " + (i + chunksize - 1) + " dòng.");
            }

            System.out.println("File CSV đã được tạo tại: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
