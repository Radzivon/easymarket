package com.easymarket.easymarket.repository;

import com.easymarket.easymarket.entity.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {
    City save(City city);
}
