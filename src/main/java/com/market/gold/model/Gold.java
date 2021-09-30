package com.market.gold.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gold {
    private String name;
    private String groupName;
    private Integer bidPrice;
    private Integer askPrice;
    private LocalDate date;
}
