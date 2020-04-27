package com.easymarket.easymarket.controller;

import com.easymarket.easymarket.entity.Cargo;
import com.easymarket.easymarket.entity.dto.CargoDto;
import com.easymarket.easymarket.exception.ResourceNotFoundException;
import com.easymarket.easymarket.service.impl.CargoServiceImpl;
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
@RequestMapping("/cargo")
@CrossOrigin(origins = "*")
public class CargoController {
    private CargoServiceImpl cargoServiceImpl;

    @Autowired
    public CargoController(CargoServiceImpl cargoServiceImpl) {
        this.cargoServiceImpl = cargoServiceImpl;
    }

    @GetMapping(value = "/{id}")
    public CargoDto cargoPage(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return Mapper.map(cargoServiceImpl.getById(id), CargoDto.class);
    }

    @GetMapping(value = {"/all"})
    public Page<CargoDto> cargoListPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
                                        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                        @RequestParam(name = "order", defaultValue = "asc") String sortDir) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.Direction.fromString(sortDir), sortBy);
        return cargoServiceImpl.getAll(paging).map(this::convertToDto);
    }

    @PostMapping(value = {"/add"})
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCargo(@Valid @RequestBody CargoDto cargoDto) {
        cargoServiceImpl.save(Mapper.map(cargoDto, Cargo.class));
    }

    @PutMapping(value = "/edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editCargo(@PathVariable("id") Long id, @Valid @RequestBody CargoDto cargoDto) throws ResourceNotFoundException {
        cargoServiceImpl.update(id, Mapper.map(cargoDto, Cargo.class));
    }
    @PutMapping(value = "/block/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void markAsPaidCargo(@PathVariable("id") Long id, @Valid @RequestBody CargoDto cargoDto) throws ResourceNotFoundException {
        cargoServiceImpl.update(id, Mapper.map(cargoDto, Cargo.class));
    }
    @DeleteMapping(value = "/delete/{id}")
    public void deleteCargo(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Cargo cargo = cargoServiceImpl.getById(id);
        cargoServiceImpl.delete(cargo);
    }

    private CargoDto convertToDto(Cargo part) {
        return Mapper.map(part, CargoDto.class);
    }
}
