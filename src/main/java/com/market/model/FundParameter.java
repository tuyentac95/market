package com.market.model;

import lombok.Builder;
import lombok.Data;

import java.util.regex.Pattern;

@Data
@Builder
public class FundParameter {
    private String baseURL;
    private Pattern pattern;
    private String name;
    private String code;
    private int dateMatchGroup;
    private int navMatchGroup;
}
