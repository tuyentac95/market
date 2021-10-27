package com.market.controller;

import com.market.model.Fund;
import com.market.serivce.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FundController {

    @Autowired
    private FundService service;

    @GetMapping("/fund/{code}")
    public ResponseEntity<Fund> getNav(@PathVariable String code) {
        try {
            Fund fund = service.fetchNav(code);
            return ResponseEntity.status(HttpStatus.OK).body(fund);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
