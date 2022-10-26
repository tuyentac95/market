package com.market.controller;

import com.market.dto.FundsDto;
import com.market.model.Fund;
import com.market.model.SFund;
import com.market.serivce.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class FundController {

    @Autowired
    private FundService service;

    @GetMapping("/fund/{code}")
    public ResponseEntity<SFund> getNav(@PathVariable String code) {
        try {
            SFund SFund = service.fetchNav(code);
            return ResponseEntity.status(HttpStatus.OK).body(SFund);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("/fund/{date}")
    public ResponseEntity<String> saveFunds(@PathVariable String date, @RequestBody FundsDto funds) {
        service.saveFunds(funds.getData(), date);
        return ResponseEntity.status(HttpStatus.OK).body("Save data of " + date);
    }

    @GetMapping("/v2/fund/{code}")
    public ResponseEntity<Fund> fetchFund(@PathVariable String code) {
        Fund f = service.findFund(code);
        if (f != null) return ResponseEntity.status(HttpStatus.OK).body(f);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
