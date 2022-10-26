package com.market.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Entity
public class GoldCategory {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String group_name;

    public GoldCategory(String name, String groupName) {
        this.name = name;
        this.group_name = groupName;
    }
}
