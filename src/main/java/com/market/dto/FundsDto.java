package com.market.dto;

import com.market.model.Fund;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundsDto {
    private List<Fund> data;
}
