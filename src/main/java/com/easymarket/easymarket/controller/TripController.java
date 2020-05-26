package com.easymarket.easymarket.controller;

import com.easymarket.easymarket.entity.Trip;
import com.easymarket.easymarket.entity.dto.TripDto;
import com.easymarket.easymarket.exception.ResourceNotFoundException;
import com.easymarket.easymarket.security.UserPrinciple;
import com.easymarket.easymarket.service.TripService;
import com.easymarket.easymarket.service.UserService;
import com.easymarket.easymarket.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public Page<TripDto> tripsPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
                                   @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                   @RequestParam(name = "order", defaultValue = "asc") String sortDir) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.Direction.fromString(sortDir), sortBy);
        return tripService.getAll(paging).map(this::convertToDto);
    }

    @GetMapping(value = {"/current"})
    public Page<TripDto> currentTripsPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
                                          @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                          @RequestParam(name = "order", defaultValue = "asc") String sortDir,
                                          @AuthenticationPrincipal UserPrinciple userPrinciple) throws ResourceNotFoundException {
        Pageable paging = PageRequest.of(page, pageSize, Sort.Direction.fromString(sortDir), sortBy);

        return tripService.getCurrentTrips(userService.getById(userPrinciple.getId()), paging).map(this::convertToDto);
    }

    @GetMapping(value = {"/cargouser"})
    public Page<TripDto> getTripsByCargoWhereUserPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                                      @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
                                                      @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                      @RequestParam(name = "order", defaultValue = "asc") String sortDir,
                                                      @AuthenticationPrincipal UserPrinciple userPrinciple) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.Direction.fromString(sortDir), sortBy);
        return tripService.getTripsByCargoOwner(userPrinciple.getId(), paging).map(this::convertToDto);
    }

    @PostMapping(value = {"/add"})
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTrip(@Valid @RequestBody TripDto tripDto,
                         @AuthenticationPrincipal UserPrinciple userPrinciple) {
        tripDto.setUserId(userPrinciple.getId());
        tripService.save(Mapper.map(tripDto, Trip.class));
    }
    @PutMapping(value = "/cancel/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void cancelTrip(@PathVariable("id") Long id, @Valid @RequestBody TripDto tripDto,
                         @AuthenticationPrincipal UserPrinciple userPrinciple) throws ResourceNotFoundException {
        tripDto.setUserId(userPrinciple.getId());
        tripService.cancel(Mapper.map(tripDto, Trip.class));
    }

    @PutMapping(value = "/edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editTrip(@PathVariable("id") Long id, @Valid @RequestBody TripDto tripDto,
                         @AuthenticationPrincipal UserPrinciple userPrinciple) throws ResourceNotFoundException {

        tripService.update(Mapper.map(tripDto, Trip.class));
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
