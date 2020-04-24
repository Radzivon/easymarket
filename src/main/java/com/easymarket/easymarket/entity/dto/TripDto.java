package com.easymarket.easymarket.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class TripDto {
    private long id;
    private String currentCity;
    private String car;
    private List<CityDto> cities;
}
