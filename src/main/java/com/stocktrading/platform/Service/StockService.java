package com.stocktrading.platform.Service;

import com.stocktrading.platform.entity.Stock;
import com.stocktrading.platform.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class StockService {

    private StockRepository stockRepository;
    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> findAllStocks()
    {
        return stockRepository.findAll();
    }




}
