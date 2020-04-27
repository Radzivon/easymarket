package com.easymarket.easymarket.repository;

import com.easymarket.easymarket.entity.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripRepository extends PagingAndSortingRepository<Trip, Long> {
    Page<Trip> findAll(Pageable pageable);

    @Query(value = "select (car, current_city,trip.is_paid, trip_condition) from ((trip inner join trip_has_cargo thc on trip.id = thc.trip_id)" +
            " inner join cargo c on thc.cargo_id = c.id) where c.user_id = :userId",
            nativeQuery = true)
    Page<Trip> findTripsByCargoOwner(@Param("userId") Long userId, Pageable pageable);

    Optional<Trip> findById(Long id);

    Trip save(Trip trip);

    void delete(Trip trip);
}
