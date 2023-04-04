package com.stocktrading.platform.Service;

import com.stocktrading.platform.entity.Market;
import com.stocktrading.platform.repository.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class MarketService {
    private MarketRepository marketRepository;

    @Autowired
    public MarketService(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }


    public String changeMarketHrs(String start, String end) {
        System.out.println(start);
        System.out.println(end);
        if (validateTime(start) && validateTime(end)) {

            LocalTime s = LocalTime.parse(start);
            LocalTime e = LocalTime.parse(end);


            if (MINUTES.between(s, e) > 1440 || e.isBefore(s)) {
                return "failed";
            }

            Market market = marketRepository.findById(1).get();
            market.setStart(s);
            market.setEnd(e);
            marketRepository.save(market);
            return "success";
        } else {
            return "failed";
        }
    }

    public boolean validateTime(String input) {
        if (input == null || input.length() != 5) {
            return false;
        }
        int time = 0;
//      try {
//           time = Integer.parseInt(input);
//      } catch (NumberFormatException e) {
//          return false;
//       }
//        if (time < 0 || time > 2359) {
//           return false;
//       }
//        int hour = time / 100;
//        int minute = time % 100;
//        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
//            return false;
//        }

        return true;
    }

}