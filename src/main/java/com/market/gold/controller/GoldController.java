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
import java.util.List;

@RestController
public class GoldController {

    @Autowired
    private GoldService service;

    @GetMapping("/gold/{date}")
    public ResponseEntity<List<Gold>> fetchPriceByDate(@PathVariable String date) {
        try {
            List<Gold> golds = service.fetchPriceByDate(date);
            return ResponseEntity.status(HttpStatus.OK).body(golds);
        } catch (IOException e) {
            System.out.println("[ERROR]");
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
