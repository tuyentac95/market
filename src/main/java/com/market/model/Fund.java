package com.market.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fund {
    private String name;
    private String code;
    private LocalDate date;
    private Double nav;
}
