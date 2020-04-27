package com.easymarket.easymarket.service;

import com.easymarket.easymarket.entity.Trip;
import com.easymarket.easymarket.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TripService {
    Page<Trip> getAll(Pageable pageable);

    Page<Trip> getTripsByCargoOwner(Long userId, Pageable pageable);

    Trip getById(Long id) throws ResourceNotFoundException;

    void save(Trip trip);

    void update(Trip oldTrip, Trip newTrip);

    void delete(Trip trip);
}
