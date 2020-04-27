package com.easymarket.easymarket.controller;

import com.easymarket.easymarket.entity.Trip;
import com.easymarket.easymarket.entity.dto.TripDto;
import com.easymarket.easymarket.exception.ResourceNotFoundException;
import com.easymarket.easymarket.service.impl.TripServiceImpl;
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
@RequestMapping("/trip")
@CrossOrigin(origins = "*")
public class TripController {
    private TripServiceImpl tripServiceImpl;

    @Autowired
    public TripController(TripServiceImpl tripServiceImpl) {
        this.tripServiceImpl = tripServiceImpl;
    }

    @GetMapping(value = "/{id}")
    public TripDto tripPage(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return Mapper.map(tripServiceImpl.getById(id), TripDto.class);
    }

    @GetMapping(value = {"/all"})
    public Page<TripDto> tripsPage(@RequestParam(defaultValue = "0") int pageNo,
                                   @RequestParam(defaultValue = "20") int pageSize,
                                   @RequestParam(defaultValue = "id") String sortBy,
                                   @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.Direction.fromString(sortDir), sortBy);
        return tripServiceImpl.getAll(paging).map(this::convertToDto);
    }

    @PostMapping(value = {"/add"})
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTrip(@Valid @RequestBody TripDto tripDto) {
        tripServiceImpl.save(Mapper.map(tripDto, Trip.class));
    }

    @PutMapping(value = "/edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editTrip(@PathVariable("id") Long id, @Valid @RequestBody TripDto tripDto) throws ResourceNotFoundException {
        Trip trip = tripServiceImpl.getById(id);
        tripServiceImpl.update(trip,Mapper.map(tripDto, Trip.class));
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteTrip(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Trip trip = tripServiceImpl.getById(id);
        tripServiceImpl.delete(trip);
    }

    private TripDto convertToDto(Trip trip) {
        return Mapper.map(trip, TripDto.class);
    }
}
