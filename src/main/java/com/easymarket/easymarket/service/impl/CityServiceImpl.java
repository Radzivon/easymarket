package com.easymarket.easymarket.service.impl;

import com.easymarket.easymarket.entity.City;
import com.easymarket.easymarket.repository.CityRepository;
import com.easymarket.easymarket.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CityServiceImpl implements CityService {
    private CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City save(City city) {
        return cityRepository.save(city);
    }

    @Override
    public List<City> saveAll(List<City> cities) {
        List<City> newSet = new ArrayList<>();
        for (City city : cities) {
            newSet.add(cityRepository.save(city));
        }
        return newSet;
    }
}
