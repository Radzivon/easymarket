package com.easymarket.easymarket.service;

import com.easymarket.easymarket.entity.Cargo;
import com.easymarket.easymarket.exception.ResourceNotFoundException;
import com.easymarket.easymarket.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CargoService {
    private CargoRepository cargoRepository;

    @Autowired
    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public Page<Cargo> getAll(Pageable pageable) {
        return cargoRepository.findAll(pageable);
    }

    public Cargo getById(Long id) throws ResourceNotFoundException {
        return cargoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cargo doesn't exist with id " + id));
    }

    public void save(Cargo cargo) {
        cargoRepository.save(cargo);
    }

    public void update(Cargo cargo) {
        cargoRepository.save(cargo);
    }

    public void delete(Cargo cargo) {
        cargoRepository.delete(cargo);
    }
}
