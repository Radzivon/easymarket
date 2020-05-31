package com.easymarket.easymarket.service;

import com.easymarket.easymarket.entity.Cargo;
import com.easymarket.easymarket.entity.CargoCondition;
import com.easymarket.easymarket.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface CargoService {
    Page<Cargo> getAll(Pageable pageable);
    Page<Cargo> getAllByUserId(Long id, Pageable pageable);
    Page<Cargo> getAllFreeCargo(Pageable pageable);
    Cargo getById(Long id) throws ResourceNotFoundException;
    List<Cargo> updateCondition(List<Cargo> cargo, CargoCondition cargoCondition);
    void update(Long id, Cargo cargo) throws ResourceNotFoundException;
    void save(Cargo cargo);
    void delete(Cargo cargo);
    void updatePaid(Long id, boolean isPaid) throws ResourceNotFoundException;
    void sendConfirmToEmail(Long cargoId, double sum) throws ResourceNotFoundException;
    void confirm(String hashConfirm) throws Exception;
}
