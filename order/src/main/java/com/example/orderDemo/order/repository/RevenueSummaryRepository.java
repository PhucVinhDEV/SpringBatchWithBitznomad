package com.example.orderDemo.order.repository;

import com.example.orderDemo.order.model.RevenueSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueSummaryRepository extends JpaRepository<RevenueSummary, String> {
}
