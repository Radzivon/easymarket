package com.easymarket.easymarket.repository;

import com.easymarket.easymarket.entity.Trip;
import com.easymarket.easymarket.entity.TripCondition;
import com.easymarket.easymarket.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripRepository extends PagingAndSortingRepository<Trip, Long> {
    Page<Trip> findAll(Pageable pageable);

    Page<Trip> findTripsByCargoUserId(Long userId, Pageable pageable);

    Page<Trip> findTripsByUserAndTripCondition(User user, TripCondition tripCondition, Pageable pageable);
    Optional<Trip> findById(Long id);

    Trip save(Trip trip);

    void delete(Trip trip);
}
