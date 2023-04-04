package com.stocktrading.platform.Controller;

import com.stocktrading.platform.Service.PersonService;
import com.stocktrading.platform.Service.*;
import com.stocktrading.platform.entity.*;
import com.stocktrading.platform.repository.MarketRepository;
import com.stocktrading.platform.repository.PersonRepository;
import com.stocktrading.platform.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class PersonController {
    private StockService stockService;
    private PersonService personService;
    private PriceService priceService;
    private TransactionService transactionService;
    private PortfolioService portfolioService;
    private OrderService orderService;
    private final PersonRepository personRepository;
    private StockRepository stockRepository;
    private UserDetailService userDetailService;
    private MarketRepository marketRepository;

    @Autowired
    public PersonController(StockService stockService, PersonService personService, PriceService priceService, TransactionService transactionService, PortfolioService portfolioService, OrderService orderService, PersonRepository personRepository, StockRepository stockRepository, UserDetailService userDetailService, MarketRepository marketRepository) {
        this.stockService = stockService;
        this.personService = personService;
        this.priceService = priceService;
        this.transactionService = transactionService;
        this.portfolioService = portfolioService;
        this.orderService = orderService;
        this.personRepository = personRepository;
        this.stockRepository = stockRepository;
        this.userDetailService = userDetailService;
        this.marketRepository = marketRepository;
    }

    @GetMapping("/stocks")
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView stocks()
    {
        List<Stock> stocks = stockService.findAllStocks();
        for(Stock s : stocks)
        {
            System.out.println(s);
        }
        ModelAndView mav = new ModelAndView("displayStocks");
        mav.addObject("stocks", stocks);

        return mav;
    }

    @GetMapping("/history")
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView transactionHistory()
    {
        String emailId = "";
        boolean status = userDetailService.userLoggedIn();
        if(!status)
            return new ModelAndView("redirect:/login");
        else
            emailId = userDetailService.getEmail();

        List<Transaction> transactions = transactionService.findAllTransactions(emailId);
        for(Transaction t : transactions)
        {
            System.out.println(t);
        }
        ModelAndView mav = new ModelAndView("displayTransactions");
        mav.addObject("transactions",transactions);
        return mav;
    }
    @GetMapping("/marketinfo")
    public ModelAndView getMarketDetails()
    {
        Market market = marketRepository.findById(1).get();
        ModelAndView mav = new ModelAndView("marketHours");
        mav.addObject("markets", market);
        return mav;
    }

    @GetMapping("/stockDetails")
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView stockDetails(@RequestParam String ticker)
    {
        Price price = priceService.getStockPriceDetails(ticker);
        System.out.println(price);
        ModelAndView mav = new ModelAndView("displayStockDetails");
        mav.addObject("price", price);
        return mav;
    }

    @PostMapping("/buy")
    @PreAuthorize("hasAuthority('USER')")
    public String buyStock(@RequestParam String ticker, @RequestParam Long quantity) {
        String emailId = "";
        boolean status = userDetailService.userLoggedIn();
        if(!status)
            return "redirect:/login";
        else
            emailId = userDetailService.getEmail();
        if(quantity <= 0)
        {
            return "redirect:/stocks";
        }
        System.out.println(ticker);
        StockBuy stockBuy = new StockBuy();
        stockBuy.setEmailId(emailId);
        stockBuy.setTicker(ticker);
        stockBuy.setQuantity(quantity);
        orderService.buy(stockBuy);
        return "redirect:/stocks";
    }
//    @PostMapping(path = "sell",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> create(@RequestBody StockBuy stockBuy) {
//
//
//    }
    @PostMapping("/sell")
    @PreAuthorize("hasAuthority('USER')")
    public String sellStock(@RequestParam String ticker, @RequestParam Long quantity) {
        String emailId = "";
        boolean status = userDetailService.userLoggedIn();
        if(!status)
            return "redirect:/login";
        else
            emailId = userDetailService.getEmail();

        if(quantity <= 0)
        {
            return "redirect:/portfolio";
        }
        StockBuy stockBuy = new StockBuy();
        stockBuy.setEmailId(emailId);
        stockBuy.setTicker(ticker);
        stockBuy.setQuantity(quantity);
        orderService.sell(stockBuy);
        return "redirect:/portfolio";
    }


    @GetMapping("/checkBalance")
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView checkBalance() {
        String emailId = "";
        boolean status = userDetailService.userLoggedIn();
        if(!status)
            return new ModelAndView("redirect:/login");
        else
            emailId = userDetailService.getEmail();
        Person person = personRepository.findById(emailId).get();
        float balance = personService.getBalance(emailId);
        System.out.println(balance);
        ModelAndView mav = new ModelAndView("account");
        mav.addObject("person", person);
        mav.addObject("balance", balance);
        return mav;
    }

   @GetMapping("/portfolio")
   @PreAuthorize("hasAuthority('USER')")
   public ModelAndView getPortfolio() {
       String emailId = "";
       boolean status = userDetailService.userLoggedIn();
       if(!status)
           return new ModelAndView("redirect:/login");
       else
           emailId = userDetailService.getEmail();
       List<Portfolio> portfolio = portfolioService.getPortfolio(emailId);
       List<Stock> stocks = new ArrayList<>();
       for(Portfolio p : portfolio)
       {
           System.out.println(p);
           stocks.add(stockRepository.findById(p.getTicker()).get());
       }

       for(Stock s : stocks)
       {
           System.out.println(s);
       }

       HashMap<String,Float> priceMap = new HashMap<>();
       for(Stock s: stocks)
           priceMap.put(s.getTicker(),s.getCurPrice());

       float totalValue = 0;
       for(Portfolio p: portfolio)
           totalValue+=priceMap.get(p.getTicker())*p.getQuantity();

       ModelAndView mav = new ModelAndView("portfolio");
       mav.addObject("portfolio", portfolio);
       mav.addObject("stocks", stocks);
       mav.addObject("prices",priceMap);
       mav.addObject("totalValue",totalValue);
       return mav;

   }

    @PostMapping("/addBalance")
    @PreAuthorize("hasAuthority('USER')")
    public String addBalance(@RequestParam float value) {
        String emailId = "";
        boolean status = userDetailService.userLoggedIn();
        if(!status)
            return "redirect:/login";
        else
            emailId = userDetailService.getEmail();

        if(value <= 0)
        {
            return "redirect:/checkBalance";
        }
        personService.addBalance(emailId, value);
        //return "redirect:/checkBalance/{emailId}";
        return "redirect:/checkBalance";
    }

    @PostMapping( "/withdrawBalance")
    @PreAuthorize("hasAuthority('USER')")
    public String withdrawBalance(@RequestParam float value) {
        String emailId = "";
        boolean status = userDetailService.userLoggedIn();
        if(!status)
            return "redirect:/login";
        else
            emailId = userDetailService.getEmail();
        if(value <= 0)
        {
            return "redirect:/checkBalance";
        }
        personService.withdrawBalance(emailId, value);

        //return "redirect:/checkBalance/{emailId}";
        return "redirect:/checkBalance";
    }



    @GetMapping(path = "/")
    public ModelAndView dashboard() {
        boolean status = userDetailService.userLoggedIn();
        System.out.println("User Logged In: "+status);

        if(status){
            boolean isAdmin = userDetailService.isAdmin();
            if(isAdmin){
                return new ModelAndView("redirect:/admin/stocks");
            }else{
                return new ModelAndView("redirect:/portfolio");
            }
        }

        ModelAndView mav = new ModelAndView("home");
        mav.addObject("status", status);
        return mav;
   }



    @GetMapping(path = "/register")
    public String registerForm() {
        System.out.println("Registering user");
        return "register";
    }
    @PostMapping(path = "/register")
    public String register(@RequestParam String name, @RequestParam String emailId, @RequestParam String password) {
        System.out.println("Registering user");
        Person p = new Person();
        p.setName(name);
        p.setEmailId(emailId);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        p.setPassword(encoder.encode(password));
        p.setBalance(0);
        p.setRoles("USER");
        personRepository.save(p);
        return "redirect:/login";
    }


}
