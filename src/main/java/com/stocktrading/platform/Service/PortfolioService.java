package com.stocktrading.platform.Service;

import com.stocktrading.platform.entity.Portfolio;
import com.stocktrading.platform.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PortfolioService {
    private PortfolioRepository portfolioRepository;

    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public List<Portfolio> getPortfolio(String emailId)
    {
        return portfolioRepository.findAllByEmailId(emailId);
    }

}
