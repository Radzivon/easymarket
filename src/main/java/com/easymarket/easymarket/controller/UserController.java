package com.easymarket.easymarket.controller;

import com.easymarket.easymarket.entity.User;
import com.easymarket.easymarket.entity.dto.CargoDto;
import com.easymarket.easymarket.entity.dto.UserDto;
import com.easymarket.easymarket.exception.ResourceNotFoundException;
import com.easymarket.easymarket.service.SenderService;
import com.easymarket.easymarket.service.UserService;
import com.easymarket.easymarket.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 10000)
public class UserController {
    private UserService userService;
    private final SenderService senderService;

    @Autowired
    public UserController(UserService userService, SenderService senderService) {
        this.userService = userService;
        this.senderService = senderService;
    }


    @GetMapping(value = {"/all"})
    public Page<UserDto> usersPage(@RequestParam(defaultValue = "0") int pageNo,
                                   @RequestParam(defaultValue = "20") int pageSize,
                                   @RequestParam(defaultValue = "id") String sortBy,
                                   @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.Direction.fromString(sortDir), sortBy);
        return userService.getAll(paging).map(this::convertToDto);
    }

    @GetMapping(value = "/{id}")
    public UserDto userPage(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return Mapper.map(userService.getById(id), UserDto.class);
    }


    @PostMapping(value = {"/add"})
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@Valid @RequestBody UserDto userDto) {
        userService.save(Mapper.map(userDto, User.class));
    }


    @PutMapping(value = "/block/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void blockUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) throws ResourceNotFoundException {
        User user = userService.getById(id);
        userService.block(user, userDto.getIsBlock());
        System.out.println(userService.getById(id));
    }

    @PutMapping(value = "/edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto) {
        userService.update(Mapper.map(userDto, User.class));
    }

    @GetMapping(value = {"/send/{username}"})
    public void test(@PathVariable String username) {
        senderService.testEmail(username);
    }


    private UserDto convertToDto(User user) {
        return Mapper.map(user, UserDto.class);
    }
}
