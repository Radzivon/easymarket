package com.easymarket.easymarket.service;

import com.easymarket.easymarket.entity.City;

import java.util.List;

public interface CityService {
    City save(City city);
    List<City> saveAll(List<City> cities);
}
