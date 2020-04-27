package com.easymarket.easymarket.service.impl;

import com.easymarket.easymarket.entity.City;
import com.easymarket.easymarket.entity.Trip;
import com.easymarket.easymarket.exception.ResourceNotFoundException;
import com.easymarket.easymarket.repository.TripRepository;
import com.easymarket.easymarket.service.CityService;
import com.easymarket.easymarket.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TripServiceImpl implements TripService {
    private TripRepository tripRepository;
    private CityService cityService;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, CityService cityService) {
        this.tripRepository = tripRepository;
        this.cityService = cityService;
    }

    @Override
    public Page<Trip> getAll(Pageable pageable) {
        return tripRepository.findAll(pageable);
    }

    @Override
    public Page<Trip> getTripsByCargoOwner(Long userId, Pageable pageable) {
        return tripRepository.findTripsByCargoOwner(userId, pageable);
    }

    @Override
    public Trip getById(Long id) throws ResourceNotFoundException {
        return tripRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Trip doesn't exist with id " + id));
    }

    @Override
    public void save(Trip trip) {
        List<City> savedCities = cityService.saveAll(trip.getCities());
        trip.setCities(savedCities);

        tripRepository.save(trip);
    }

    @Override
    public void update(Trip oldTrip, Trip newTrip) {
        tripRepository.save(newTrip);
    }

    @Override
    public void delete(Trip trip) {
        tripRepository.delete(trip);
    }
}
