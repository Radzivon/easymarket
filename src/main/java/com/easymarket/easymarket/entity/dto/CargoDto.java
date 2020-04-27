package com.easymarket.easymarket.entity.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CargoDto {
    private long id;
    private String name;
    private String cargoCondition;
    private float weight;
    private float width;
    private float length;
    private float height;
    private Boolean isPaid;
    private String location;
    private BigDecimal transportationCost;

    private long userId;
}
