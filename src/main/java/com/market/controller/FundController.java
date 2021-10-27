package com.market.controller;

import com.market.model.Fund;
import com.market.serivce.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FundController {

    @Autowired
    private FundService service;

    @GetMapping("/fund/bvpf")
    public ResponseEntity<Fund> getBvpf() {
        try {
            Fund BVPF = service.fetchNav();
            return ResponseEntity.status(HttpStatus.OK).body(BVPF);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/fund/pvbf")
    public ResponseEntity<Fund> getPvbf() {
        try {
            Fund PVBF = service.fetchPvbf();
            return ResponseEntity.status(HttpStatus.OK).body(PVBF);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
