package com.easymarket.easymarket.controller;

import com.easymarket.easymarket.entity.Cargo;
import com.easymarket.easymarket.entity.dto.CargoDto;
import com.easymarket.easymarket.exception.ResourceNotFoundException;
import com.easymarket.easymarket.service.CargoService;
import com.easymarket.easymarket.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class CargoController {
    private CargoService cargoService;

    @Autowired
    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping(value = "cargo/{id}")
    public CargoDto cargoPage(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return Mapper.map(cargoService.getById(id), CargoDto.class);
    }

    @GetMapping(value = {"cargo/all"})
    public Page<CargoDto> cargoListPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
                                        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                        @RequestParam(name = "order", defaultValue = "asc") String sortDir) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.Direction.fromString(sortDir), sortBy);
        return cargoService.getAll(paging).map(this::convertToDto);
    }

    @GetMapping(value = {"cargo/user/{userId}"})
    public Page<CargoDto> cargoListByUserIdPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                                @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
                                                @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                @RequestParam(name = "order", defaultValue = "asc") String sortDir,
                                                @PathVariable("userId") Long userId) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.Direction.fromString(sortDir), sortBy);
        return cargoService.getAllByUserId(userId, paging).map(this::convertToDto);
    }

    @PostMapping(value = {"cargo/add"})
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCargo(@Valid @RequestBody CargoDto cargoDto) {
        cargoService.save(Mapper.map(cargoDto, Cargo.class));
    }

    @PutMapping(value = "cargo/edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editCargo(@PathVariable("id") Long id, @Valid @RequestBody CargoDto cargoDto) throws ResourceNotFoundException {
        cargoService.update(id, Mapper.map(cargoDto, Cargo.class));
    }

    @PutMapping(value = "cargo/paid/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void markAsPaidCargo(@PathVariable("id") Long id, @RequestBody Boolean isPaid) throws ResourceNotFoundException {
        cargoService.updatePaid(id, isPaid);
    }

    @DeleteMapping(value = "cargo/delete/{id}")
    public void deleteCargo(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Cargo cargo = cargoService.getById(id);
        cargoService.delete(cargo);
    }

    private CargoDto convertToDto(Cargo part) {
        return Mapper.map(part, CargoDto.class);
    }
}
