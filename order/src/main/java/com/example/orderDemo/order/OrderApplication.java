package com.example.orderDemo.order;

import com.example.orderDemo.order.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class OrderApplication {
	private final JobLauncher jobLauncher;
	private final Job revenueCalculationJob;
	private final ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
//		WriteFile.turnOnWritedInput(100000,10000);
	}

	@Bean
	public CommandLineRunner run() {
		return args -> {
			// Tạo JobParameters
			JobParameters jobParameters = new JobParametersBuilder()
					.addLong("startAt", System.currentTimeMillis())
					.toJobParameters();

			// Chạy job
			jobLauncher.run(revenueCalculationJob, jobParameters);
			System.out.println("Job đã được chạy thành công!");
		};
	}
}
