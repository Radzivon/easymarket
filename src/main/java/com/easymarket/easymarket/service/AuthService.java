package com.easymarket.easymarket.service;


import com.easymarket.easymarket.entity.dto.UserDto;
import com.easymarket.easymarket.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity signUp(UserDto signUpForm) throws ResourceNotFoundException;

    ResponseEntity signIn(UserDto loginForm);
}
