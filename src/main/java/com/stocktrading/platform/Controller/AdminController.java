package com.stocktrading.platform.Controller;

import com.stocktrading.platform.Service.*;
import com.stocktrading.platform.entity.Market;
import com.stocktrading.platform.entity.Price;
import com.stocktrading.platform.Service.StockService;
import com.stocktrading.platform.entity.Stock;
import com.stocktrading.platform.repository.MarketRepository;
import com.stocktrading.platform.repository.PriceRepository;
import com.stocktrading.platform.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminController {

    private PriceService priceService;
    private StockRepository stockRepository;
    private PriceRepository priceRepository;
    private MarketService marketService;
    private StockService stockService;
    private UserDetailService userDetailService;
    private MarketRepository marketRepository;


    @Autowired
    public AdminController(PriceService priceService, StockRepository stockRepository, PriceRepository priceRepository, MarketService marketService, StockService stockService, UserDetailService userDetailService, MarketRepository marketRepository) {
        this.priceService = priceService;
        this.stockRepository = stockRepository;
        this.priceRepository = priceRepository;
        this.marketService = marketService;
        this.stockService = stockService;
        this.userDetailService = userDetailService;
        this.marketRepository = marketRepository;
    }

    @GetMapping("/create/stocks")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getstocks()
    {
        return "createStock";
    }

    @PostMapping(path = "/produce/stocks")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String create(@RequestParam String ticker, @RequestParam String name, @RequestParam float curPrice, @RequestParam Long volume) {
        if(stockRepository.existsById(ticker) || curPrice < 0 || volume <=0)
        {
            return "redirect:/admin/stocks";
        }

        Stock stock = new Stock();
        stock.setVolume(volume);
        stock.setAvailable(volume);
        stock.setCurPrice(curPrice);
        stock.setName(name);
        stock.setTicker(ticker);
        System.out.println(stock);
        stockRepository.save(stock);

        Price price = new Price();
        price.setTicker(ticker);
        price.setHigh(curPrice);
        price.setLow(curPrice);
        price.setOpen(curPrice);
        priceRepository.save(price);
        return "redirect:/admin/stocks";
        //System.out.println(stockRepository.findAll());
    }
    @GetMapping("/adminstockDetails")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView stockDetails(@RequestParam String ticker)
    {
        Price price = priceService.getStockPriceDetails(ticker);
        System.out.println(price);
        ModelAndView mav = new ModelAndView("displayAdminStockDetails");
        mav.addObject("price", price);
        return mav;
    }

    @GetMapping("/updateHours")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView change()
    {
       ModelAndView mav = new ModelAndView("updateHours");
        Market market = marketRepository.findById(1).get();
        mav.addObject("markets", market);
        return mav;
    }

    @PostMapping("/change")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String changeMarketHours(@RequestParam String start, @RequestParam String end)
    {
        try{
            String s = marketService.changeMarketHrs(start,end);
            System.out.println(s);
            return "redirect:/updateHours";
        }catch (Exception e){
            return "redirect:/updateHours";
        }
    }

    @GetMapping("/admin/stocks")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView stocks()
    {
        List<Stock> stocks = stockService.findAllStocks();
        ModelAndView mav = new ModelAndView("displayAdminStocks");
        mav.addObject("stocks", stocks);
        return mav;
    }




}
