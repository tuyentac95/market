package com.market.controller;

import com.market.dto.DateRangeParams;
import com.market.model.Gold;
import com.market.model.GoldCategory;
import com.market.serivce.GoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/")
    public String welcome() {
        return "This application is ready!";
    }

    @GetMapping("/gold/type")
    public ResponseEntity<List<GoldCategory>> getCategories() {
        List<GoldCategory> categories = service.getCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @GetMapping("/gold/type/{id}")
    public ResponseEntity<GoldCategory> getCategory(@PathVariable Long id) {
        GoldCategory category = service.getCategory(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @PostMapping("/fetch-gold/type/{id}")
    public ResponseEntity<List<Gold>> fetchPriceByType(@PathVariable Long id, @RequestBody DateRangeParams range) {
        LocalDate from = LocalDate.parse(range.getFrom(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate to = LocalDate.parse(range.getTo(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Gold> golds = service.fetchPriceByType(id, from, to);
        if (golds != null) {
            return ResponseEntity.status(HttpStatus.OK).body(golds);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


}
