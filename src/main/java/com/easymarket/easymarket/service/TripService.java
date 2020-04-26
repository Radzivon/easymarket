package com.easymarket.easymarket.service;

import com.easymarket.easymarket.entity.Trip;
import com.easymarket.easymarket.exception.ResourceNotFoundException;
import com.easymarket.easymarket.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TripService {
    private TripRepository tripRepository;

    @Autowired
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public Page<Trip> getAll(Pageable pageable) {
        return tripRepository.findAll(pageable);
    }

    public Trip getById(Long id) throws ResourceNotFoundException {
        return tripRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Trip doesn't exist with id " + id));
    }

    public void save(Trip trip) {
        tripRepository.save(trip);
    }

    public void update(Trip oldTrip, Trip newTrip) {
        tripRepository.save(newTrip);
    }

    public void delete(Trip trip) {
        tripRepository.delete(trip);
    }
}
