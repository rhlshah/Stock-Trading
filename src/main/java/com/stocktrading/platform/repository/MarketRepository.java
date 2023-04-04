package com.stocktrading.platform.repository;

import com.stocktrading.platform.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Market, Integer> {

}
