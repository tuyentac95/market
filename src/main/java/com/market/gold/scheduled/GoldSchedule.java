package com.market.gold.scheduled;

import com.market.gold.model.Gold;
import com.market.gold.serivce.GoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.time.LocalDate;

@Configuration
@EnableScheduling
public class GoldSchedule {

    @Autowired
    private GoldService service;

    @Scheduled(cron = "0 32 20 * * ?", zone = "Asia/Bangkok")
    private void scheduleJob() {
        System.out.println("[INFO] Start cron job: Fetching price of gold today");
        try {
            LocalDate today = LocalDate.now();
            service.fetchPriceByDate(today);
        } catch (IOException e) {
            System.out.println("[ERROR]");
            e.printStackTrace();
        }
    }
}
