package com.easymarket.easymarket.service.impl;

import com.easymarket.easymarket.entity.Trip;
import com.easymarket.easymarket.exception.ResourceNotFoundException;
import com.easymarket.easymarket.repository.TripRepository;
import com.easymarket.easymarket.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TripServiceImpl implements TripService {
    private TripRepository tripRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public Page<Trip> getAll(Pageable pageable) {
        return tripRepository.findAll(pageable);
    }

    @Override
    public Trip getById(Long id) throws ResourceNotFoundException {
        return tripRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Trip doesn't exist with id " + id));
    }

    @Override
    public void save(Trip trip) {
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
