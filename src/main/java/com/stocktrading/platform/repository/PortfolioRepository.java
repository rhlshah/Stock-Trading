package com.stocktrading.platform.repository;

import com.stocktrading.platform.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, String> {
    boolean existsByEmailIdAndTicker(String emailId, String ticker);
    Portfolio findByEmailIdAndTicker(String emailId, String ticker);

    List<Portfolio> findAllByEmailId(String emailId);

    void deleteByEmailIdAndTicker(String emailId, String ticker);
    //@Query("SELECT Portfolio.Ticker, Portfolio.SUM(Quantity) FROM Portfolio GROUP BY Portfolio.Ticker HAVING Portfolio.EmailAddress = ?1")
    // public Object[] getPortfolio(String emailId);
}
