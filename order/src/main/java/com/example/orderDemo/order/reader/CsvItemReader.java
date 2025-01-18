package com.example.orderDemo.order.reader;

import com.example.orderDemo.order.model.Order;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class CsvItemReader {



    @Bean
    public FlatFileItemReader<Order> reader(@Value("${spring.input.file.path}") String filePath) throws IOException {
        FlatFileItemReader<Order> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new ClassPathResource(filePath));
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return  itemReader;
    }

    private LineMapper<Order> lineMapper() {
        DefaultLineMapper<Order> lineMapper = new DefaultLineMapper<>();

        // Cau hinh LineTokenizer
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("orderId", "productId", "quantity", "price"); // Tên cột trong file CSV

        // Cau hinh FieldSetMapper
        BeanWrapperFieldSetMapper<Order> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Order.class);

        // Gán LineTokenizer và FieldSetMapper vào LineMapper
        lineMapper.setFieldSetMapper(fieldSetMapper);
        lineMapper.setLineTokenizer(lineTokenizer);
        return lineMapper;
    }
}
