package com.market.gold.controller;

import com.market.gold.model.Gold;
import com.market.gold.serivce.GoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class GoldController {

    @Autowired
    private GoldService service;

    @GetMapping("/gold/{date}")
    public ResponseEntity<List<Gold>> getPriceByDate(@PathVariable String date) {
        List<Gold> golds = service.getPriceByDate(date);
        if (!golds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(golds);
        }
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.fetchPriceByDate(date));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
    }

    @GetMapping("/fetch-gold/{month}")
    public ResponseEntity<String> fetchPrice(@PathVariable String month) {
        service.fetchPriceByMonth(month);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

}
