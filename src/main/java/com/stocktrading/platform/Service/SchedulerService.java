package com.stocktrading.platform.Service;

import com.stocktrading.platform.Service.PriceService;
import com.stocktrading.platform.entity.Market;
import com.stocktrading.platform.entity.Person;
import com.stocktrading.platform.entity.Price;
import com.stocktrading.platform.entity.Stock;
import com.stocktrading.platform.repository.MarketRepository;
import com.stocktrading.platform.repository.PersonRepository;
import com.stocktrading.platform.repository.PriceRepository;
import com.stocktrading.platform.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.error.Mark;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Component
@EnableScheduling
public class SchedulerService {

    @Autowired
    private PriceService priceService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PriceRepository priceRepository;
    //@Autowired

    @Scheduled(initialDelay = 100, fixedDelay=Long.MAX_VALUE)
    public void initDatabase() {
        List<Market> markets = marketRepository.findAll();
        if(markets.size() == 0){
            Market market = new Market();
            market.setStart(LocalTime.of(9,30,0));
            market.setEnd(LocalTime.of(17,30,0));
            marketRepository.save(market);

            Person person = new Person();
            person.setRoles("ADMIN");
            person.setEmailId("admin27@gmail.com");
            person.setName("admin27");
            person.setBalance(0);
            person.setPassword("$2a$10$caukOr3EspGOZyARgZLKUuGMS6mmKZZCRCPmL9u/km/vMpLIxneFm");
            personRepository.save(person);
        }
    }


    @Scheduled(fixedRate = 60000) // 1 minute = 60,000 milliseconds
    public void runEveryMinute() {
        if(!marketRepository.existsById(1))
        {
            return;
        }

        Market market = marketRepository.findById(1).get();
        if(LocalTime.now().equals(market.getStart()))
        {
            List<Stock> stocks = stockRepository.findAll();
            for(Stock stockel : stocks)
            {
                float curPrice = stockel.getCurPrice();
                Price price = priceRepository.findByTicker(stockel.getTicker());
                price.setOpen(curPrice);
                price.setHigh(curPrice);
                price.setLow(curPrice);
                priceRepository.save(price);
            }
        }
        else if(LocalTime.now().isAfter(market.getStart()) && LocalTime.now().isBefore(market.getEnd()))
        {
            List<Stock> stocks = stockRepository.findAll();
            for(Stock stockel : stocks)
            {
                float curPrice = stockel.getCurPrice();
                Price price = priceRepository.findByTicker(stockel.getTicker());
                //price.setOpen(curPrice);
                price.setHigh(max(price.getHigh(), curPrice));
                price.setLow(min(price.getLow(),curPrice));
                priceRepository.save(price);
            }
        }
        // your scheduled task logic here
    }

    @Scheduled(fixedRateString = "300000")
    public void fluctuatePrice(){
        List<Stock> stockList = stockRepository.findAll();

        for(Stock el: stockList){
            Random random = new Random();
            float randomNum = -0.1f + (0.1f - -0.1f) * random.nextFloat();
            float stockPrice = el.getCurPrice();
            stockPrice = stockPrice + stockPrice*randomNum;
            if(stockPrice < 0)
            {
                stockPrice = 0;
            }
            el.setCurPrice(stockPrice);
            //System.out.println(el);
        }
        stockRepository.saveAll(stockList);

    }
}
