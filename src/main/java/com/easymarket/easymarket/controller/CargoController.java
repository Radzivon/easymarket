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
@RequestMapping("/cargo")
@CrossOrigin(origins = "http://localhost:4200")
public class CargoController {
    private CargoService cargoService;

    @Autowired
    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }
    @GetMapping(value = "/{id}")
    public CargoDto cargoPage(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return Mapper.map(cargoService.getById(id), CargoDto.class);
    }

    @GetMapping(value = {"/all"})
    public Page<CargoDto> cargosPage(@RequestParam(defaultValue = "0") int pageNo,
                                    @RequestParam(defaultValue = "20") int pageSize,
                                    @RequestParam(defaultValue = "id") String sortBy,
                                    @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.Direction.fromString(sortDir), sortBy);
        return cargoService.getAll(paging).map(this::convertToDto);
    }

    @PostMapping(value = {"/add"})
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCargo(@Valid @RequestBody CargoDto cargoDto) {
        cargoService.save(Mapper.map(cargoDto, Cargo.class));
    }

    @PutMapping(value = "/edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editCargo(@PathVariable("id") Long id, @Valid @RequestBody CargoDto cargoDto) {
        cargoService.update(Mapper.map(cargoDto, Cargo.class));
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteCargo(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Cargo cargo = cargoService.getById(id);
        cargoService.delete(cargo);
    }

    private CargoDto convertToDto(Cargo part) {
        return Mapper.map(part, CargoDto.class);
    }
}
