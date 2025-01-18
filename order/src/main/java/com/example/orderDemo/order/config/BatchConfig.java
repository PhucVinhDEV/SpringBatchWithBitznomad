package com.example.orderDemo.order.config;

import com.example.orderDemo.order.listener.RevenueThresholdListener;
import com.example.orderDemo.order.model.Order;
import com.example.orderDemo.order.model.RevenueSummary;
import com.example.orderDemo.order.reader.CsvItemReader;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;


    @Bean
    public Job revenueCalculationJob(Step step1, RevenueThresholdListener listener) {
        return new JobBuilder("revenueCalculationJob", jobRepository)
                .start(step1) // Xử lý dữ liệu từ CSV
//                .next(step2)  // Xử lý dữ liệu từ API
                .listener(listener)
                .build();
    }

    @Bean
    public Step step1(@Qualifier("reader") ItemReader<Order> csvItemReader,
                      ItemProcessor<Order, RevenueSummary> processor,
                      ItemWriter<RevenueSummary> writer) {
        return new StepBuilder("step1", jobRepository)
                .<Order, RevenueSummary>chunk(1000, transactionManager)
                .reader(csvItemReader)
                .processor(processor)
                .writer(writer)
                .build();
    }

//    @Bean
//    public Step step2(ItemReader<Order> apiItemReader, ItemProcessor<Order, RevenueSummary> processor, ItemWriter<RevenueSummary> writer) {
//        return new StepBuilder("step2", jobRepository)
//                .<Order, RevenueSummary>chunk(1000, transactionManager)
//                .reader(apiItemReader)
//                .processor(processor)
//                .writer(writer)
//                .build();
//    }
}

