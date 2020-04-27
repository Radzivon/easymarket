package com.easymarket.easymarket.controller;

import com.easymarket.easymarket.entity.User;
import com.easymarket.easymarket.entity.dto.UserDto;
import com.easymarket.easymarket.exception.ResourceNotFoundException;
import com.easymarket.easymarket.service.impl.UserServiceImpl;
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
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    private UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    @GetMapping(value = {"/all"})
    public Page<UserDto> usersPage(@RequestParam(defaultValue = "0") int pageNo,
                                   @RequestParam(defaultValue = "20") int pageSize,
                                   @RequestParam(defaultValue = "id") String sortBy,
                                   @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.Direction.fromString(sortDir), sortBy);
        return userServiceImpl.getAll(paging).map(this::convertToDto);
    }

    @GetMapping(value = "/{id}")
    public UserDto userPage(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return Mapper.map(userServiceImpl.getById(id), UserDto.class);
    }


    @PostMapping(value = {"/add"})
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@Valid @RequestBody UserDto userDto) {
        userServiceImpl.save(Mapper.map(userDto, User.class));
    }


    @PutMapping(value = "/block/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void blockUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) throws ResourceNotFoundException {
        User user = userServiceImpl.getById(id);
        userServiceImpl.block(user, userDto.getIsBlock());
        System.out.println(userServiceImpl.getById(id));
    }

    @PutMapping(value = "/edit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto) {
        userServiceImpl.update(Mapper.map(userDto, User.class));
    }

    private UserDto convertToDto(User user) {
        return Mapper.map(user, UserDto.class);
    }
}
