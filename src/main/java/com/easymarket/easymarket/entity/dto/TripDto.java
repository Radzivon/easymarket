package com.easymarket.easymarket.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class TripDto {
    private Long id;
    private String currentCity;
    private String car;
    private String tripCondition;
    private Boolean isPaid;
    private List<CityDto> cities;
    private List<CargoDto> cargo;
    private Long userId;
}
