package com.market.controller;

import com.market.dto.StocksDto;
import com.market.model.Stock;
import com.market.serivce.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StockController {

    @Autowired
    private StockService service;

    @GetMapping("/stock/{code}")
    public ResponseEntity<Stock> fetchStock(@PathVariable String code) {
        Stock s = service.findStock(code);
        if (s != null) return ResponseEntity.status(HttpStatus.OK).body(s);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("/stock/{date}")
    public ResponseEntity<String> saveStocks(@PathVariable String date, @RequestBody StocksDto stocks) {
        service.saveStocks(stocks.getData(), date);
        return ResponseEntity.status(HttpStatus.OK).body("Save data of " + date);
    }
}
