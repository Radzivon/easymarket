package com.easymarket.easymarket.repository;

import com.easymarket.easymarket.entity.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripRepository extends PagingAndSortingRepository<Trip, Long> {
    Page<Trip> findAll(Pageable pageable);

    Optional<Trip> findById(Long id);

    Trip save(Trip trip);

    void delete(Trip trip);
}
