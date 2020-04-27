package com.easymarket.easymarket.repository;

import com.easymarket.easymarket.entity.Cargo;
import com.easymarket.easymarket.entity.CargoCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoRepository extends PagingAndSortingRepository<Cargo, Long> {
    Page<Cargo> findAll(Pageable pageable);

    Optional<Cargo> findById(Long id);

    Cargo save(Cargo cargo);

    void delete(Cargo cargo);

    Page<Cargo> findAllByUserId(Long userId, Pageable pageable);

    Page<Cargo> findAllByCargoCondition(CargoCondition cargoCondition, Pageable pageable);
}
