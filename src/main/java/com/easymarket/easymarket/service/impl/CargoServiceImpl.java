package com.easymarket.easymarket.service.impl;

import com.easymarket.easymarket.entity.Cargo;
import com.easymarket.easymarket.entity.CargoCondition;
import com.easymarket.easymarket.exception.ResourceNotFoundException;
import com.easymarket.easymarket.repository.CargoRepository;
import com.easymarket.easymarket.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CargoServiceImpl implements CargoService {
    private CargoRepository cargoRepository;

    @Autowired
    public CargoServiceImpl(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @Override
    public Page<Cargo> getAll(Pageable pageable) {
        return cargoRepository.findAll(pageable);
    }

    @Override
    public Page<Cargo> getAllByUserId(Long id, Pageable pageable) {
        return cargoRepository.findAllByUserId(id, pageable);
    }

    @Override
    public Page<Cargo> getAllFreeCargo(Pageable pageable) {
        return cargoRepository.findAllByCargoCondition(CargoCondition.FREE, pageable);
    }

    @Override
    public Cargo getById(Long id) throws ResourceNotFoundException {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cargo doesn't exist with id " + id));
    }

    @Override
    public void update(Long id, Cargo cargo) throws ResourceNotFoundException {
        Cargo oldCargo = cargoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cargo doesn't exist with id " + id));
//todo
        cargoRepository.save(oldCargo);
    }

    @Override
    public void save(Cargo cargo) {
        cargoRepository.save(cargo);
    }

    @Override
    public void delete(Cargo cargo) {
        cargoRepository.delete(cargo);
    }

    @Override
    public void updatePaid(Long id, boolean isPaid) throws ResourceNotFoundException {
        Cargo oldCargo = cargoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cargo doesn't exist with id " + id));
        oldCargo.setIsPaid(isPaid);
        cargoRepository.save(oldCargo);
    }
}
