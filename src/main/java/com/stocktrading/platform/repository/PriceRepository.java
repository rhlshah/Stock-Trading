package com.stocktrading.platform.repository;

import com.stocktrading.platform.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    Price findByTicker(String ticker);
}
