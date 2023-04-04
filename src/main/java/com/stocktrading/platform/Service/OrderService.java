package com.stocktrading.platform.Service;

import com.stocktrading.platform.entity.*;
import com.stocktrading.platform.repository.MarketRepository;
import com.stocktrading.platform.repository.PortfolioRepository;
import com.stocktrading.platform.repository.StockRepository;
import com.stocktrading.platform.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class OrderService {
    private MarketRepository marketRepository;
    private PersonService personService;
    private StockRepository stockRepository;
    private PortfolioRepository portfolioRepository;
    private TransactionRepository transactionRepository;

    private final String BUY = "BUY";
    private final String SELL = "SELL";


    @Autowired
    public OrderService(MarketRepository marketRepository, PersonService personService, StockRepository stockRepository, PortfolioRepository portfolioRepository, TransactionRepository transactionRepository) {
        this.marketRepository = marketRepository;
        this.personService = personService;
        this.stockRepository = stockRepository;
        this.portfolioRepository = portfolioRepository;
        this.transactionRepository = transactionRepository;
    }

    public String buy(StockBuy stockBuy)
    {
        LocalTime local = LocalTime.now();
        Market market = marketRepository.findById(1).get();
        LocalTime start = market.getStart();
        LocalTime end = market.getEnd();
        if(local.isBefore(end) && local.isAfter(start))
        {
            float balance = personService.getBalance(stockBuy.getEmailId());
            Stock stockEntry = stockRepository.findById(stockBuy.getTicker()).get();
            System.out.println(stockEntry);
            float stockPrice = stockEntry.getCurPrice();
            if(stockBuy.getQuantity() > stockEntry.getAvailable())
            {
                return "Failed";
            }
            if(balance < stockBuy.getQuantity()*stockPrice)
            {
                return "Failed";
            }

            //create portfolio entry
            if(portfolioRepository.existsByEmailIdAndTicker(stockBuy.getEmailId(), stockBuy.getTicker()))
            {
                Portfolio portfolio = portfolioRepository.findByEmailIdAndTicker(stockBuy.getEmailId(), stockBuy.getTicker());
                portfolio.setQuantity(portfolio.getQuantity() + stockBuy.getQuantity());
                portfolioRepository.save(portfolio);
            }
            else {
                Portfolio portfolio = new Portfolio();
                portfolio.setQuantity(stockBuy.getQuantity());
                portfolio.setTicker(stockBuy.getTicker());
                portfolio.setEmailId(stockBuy.getEmailId());
                portfolioRepository.save(portfolio);
            }

            //withdraw balance from user
            personService.withdrawBalance(stockBuy.getEmailId(), stockBuy.getQuantity()*stockPrice);

            //reduce the stock from available volume
            Stock stock = stockRepository.findById(stockBuy.getTicker()).get();
            //stock.setVolume(stock.getVolume() - stockBuy.getQuantity());
            stock.setAvailable(stock.getAvailable() - stockBuy.getQuantity());
            stockRepository.save(stock);

            //record the transaction

            Transaction transaction = new Transaction();
            transaction.setTicker(stockBuy.getTicker());
            transaction.setPrice(stockPrice);
            transaction.setEmailId(stockBuy.getEmailId());
            transaction.setDate(LocalDate.now());
            transaction.setStatus(BUY);
            transaction.setQuantity(stockBuy.getQuantity());
            transactionRepository.save(transaction);
            return "executed";
        }
        return "failed";
    }

    public String sell(StockBuy stockBuy)
    {
        LocalTime local = LocalTime.now();
        Market market = marketRepository.findById(1).get();
        LocalTime start = market.getStart();
        LocalTime end = market.getEnd();
        if(local.isBefore(end) && local.isAfter(start))
        {
            Portfolio portfolio = portfolioRepository.findByEmailIdAndTicker(stockBuy.getEmailId(), stockBuy.getTicker());
            Long stocksOwned = portfolio.getQuantity();
            if(stockBuy.getQuantity() > stocksOwned)
            {
                return "failed";
            }
            //create portfolio entry
            //Long quantity = stocksOwned - stockBuy.getQuantity();
            stocksOwned -= stockBuy.getQuantity();
            if(stocksOwned == 0)
            {

                Portfolio entry = portfolioRepository.findByEmailIdAndTicker(stockBuy.getEmailId(),stockBuy.getTicker());
                portfolioRepository.delete(entry);
            }else{
                portfolio.setQuantity(stocksOwned);
                portfolioRepository.save(portfolio);
            }

            //add balance
            float curPrice = stockRepository.findById(stockBuy.getTicker()).get().getCurPrice();
            personService.addBalance(stockBuy.getEmailId(), stockBuy.getQuantity()*curPrice);


            //add volume
            Stock stock = stockRepository.findById(stockBuy.getTicker()).get();
           stock.setAvailable(stock.getAvailable()+stockBuy.getQuantity());
            // stock.setVolume(stock.getVolume() + quantity);
            stockRepository.save(stock);


            //record transaction
            Transaction transaction = new Transaction();
            transaction.setTicker(stockBuy.getTicker());
            transaction.setPrice(curPrice);
            transaction.setEmailId(stockBuy.getEmailId());
            transaction.setDate(LocalDate.now());
            transaction.setStatus(SELL);
            transaction.setQuantity(stockBuy.getQuantity());
            transactionRepository.save(transaction);
            return "executed";
        }
        return "failed";
    }

}
