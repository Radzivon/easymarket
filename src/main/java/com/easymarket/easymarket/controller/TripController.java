package com.easymarket.easymarket.controller;

import com.easymarket.easymarket.entity.Trip;
import com.easymarket.easymarket.entity.dto.TripDto;
import com.easymarket.easymarket.exception.ResourceNotFoundException;
import com.easymarket.easymarket.service.TripService;
import com.easymarket.easymarket.service.UserService;
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
    private TripService tripService;
    private UserService userService;

    @Autowired
    public TripController(TripService tripService, UserService userService) {
        this.tripService = tripService;
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public TripDto tripPage(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return Mapper.map(tripService.getById(id), TripDto.class);
    }

    @GetMapping(value = {"/all"})
    public Page<TripDto> tripsPage(@RequestParam(defaultValue = "0") int pageNo,
                                   @RequestParam(defaultValue = "20") int pageSize,
                                   @RequestParam(defaultValue = "id") String sortBy,
                                   @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.Direction.fromString(sortDir), sortBy);
        return tripService.getAll(paging).map(this::convertToDto);
    }

    @GetMapping(value = {"/trip/current/{userId}"})
    public Page<TripDto> currentTripsPage(@RequestParam(defaultValue = "0") int pageNo,
                                          @RequestParam(defaultValue = "20") int pageSize,
                                          @RequestParam(defaultValue = "id") String sortBy,
                                          @RequestParam(defaultValue = "asc") String sortDir,
                                          @PathVariable("userId") Long userId) throws ResourceNotFoundException {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.Direction.fromString(sortDir), sortBy);

        return tripService.getCurrentTrips(userService.getById(userId), paging).map(this::convertToDto);
    }

    @GetMapping(value = {"/cargo/user/{userId}"})
    public Page<TripDto> getTripsByCargoWhereUserPage(@RequestParam(defaultValue = "0") int pageNo,
                                                      @RequestParam(defaultValue = "20") int pageSize,
                                                      @RequestParam(defaultValue = "id") String sortBy,
                                                      @RequestParam(defaultValue = "asc") String sortDir,
                                                      @PathVariable("userId") Long userId) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.Direction.fromString(sortDir), sortBy);
        return tripService.getTripsByCargoOwner(userId, paging).map(this::convertToDto);
    }

    @PostMapping(value = {"/add"})
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTrip(@Valid @RequestBody TripDto tripDto) {
        tripService.save(Mapper.map(tripDto, Trip.class));
    }

    @PutMapping(value = "/edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editTrip(@PathVariable("id") Long id, @Valid @RequestBody TripDto tripDto) throws ResourceNotFoundException {
        Trip trip = tripService.getById(id);
        tripService.update(trip, Mapper.map(tripDto, Trip.class));
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteTrip(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Trip trip = tripService.getById(id);
        tripService.delete(trip);
    }

    private TripDto convertToDto(Trip trip) {
        return Mapper.map(trip, TripDto.class);
    }
}
