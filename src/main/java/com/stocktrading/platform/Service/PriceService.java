package com.stocktrading.platform.Service;

import com.stocktrading.platform.entity.Price;
import com.stocktrading.platform.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {
    private PriceRepository priceRepository;
    @Autowired
    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Price getStockPriceDetails(String ticker)
    {
        return priceRepository.findByTicker(ticker);
    }

    public void fluctuatePrice()
    {

    }

}
