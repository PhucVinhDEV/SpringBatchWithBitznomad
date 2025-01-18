package com.example.orderDemo.order.writer;

import com.example.orderDemo.order.model.RevenueSummary;
import com.example.orderDemo.order.repository.RevenueSummaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class DatabaseItemWriter implements ItemWriter<RevenueSummary> {
    private final RevenueSummaryRepository revenueSummaryRepository;

    @Override
    public void write(Chunk<? extends RevenueSummary> chunk) throws Exception {
        chunk.forEach(
                revenueSummary -> {
                    RevenueSummary existingSummary = revenueSummaryRepository.findById(revenueSummary.getCategoryName())
                            .orElse(new RevenueSummary(revenueSummary.getCategoryName(), 0.0));
                    existingSummary.setRevenue(existingSummary.getRevenue() + revenueSummary.getRevenue());
                    revenueSummaryRepository.save(existingSummary);
                }
        );
    }
}
