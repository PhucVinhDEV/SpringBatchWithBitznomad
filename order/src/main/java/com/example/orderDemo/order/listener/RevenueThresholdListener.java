package com.example.orderDemo.order.listener;

import com.example.orderDemo.order.model.RevenueSummary;
import com.example.orderDemo.order.repository.RevenueSummaryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class RevenueThresholdListener implements JobExecutionListener {
    private final RevenueSummaryRepository revenueSummaryRepository;
    private static final double THRESHOLD = 50000.0;

    @Override
    public void afterJob(JobExecution jobExecution) {
        List<RevenueSummary> summaries = revenueSummaryRepository.findAll();
        summaries.forEach(summary -> {
            if (summary.getRevenue() > THRESHOLD) {
                sendNotification(summary.getCategoryName(), summary.getRevenue());
            }
        });
    }

    private void sendNotification(String category, double totalRevenue) {
        // Gửi thông báo qua email, Slack, hoặc hệ thống quản lý
        System.out.println("ALERT: Category " + category + " has exceeded the threshold with revenue: " + totalRevenue);
    }
}
