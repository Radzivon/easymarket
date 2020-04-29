package com.easymarket.easymarket.service.impl;

import com.easymarket.easymarket.entity.User;
import com.easymarket.easymarket.entity.dto.UserDto;
import com.easymarket.easymarket.exception.ResourceNotFoundException;
import com.easymarket.easymarket.repository.UserRepository;
import com.easymarket.easymarket.security.jwt.JwtProvider;
import com.easymarket.easymarket.security.message.response.JwtResponse;
import com.easymarket.easymarket.security.message.response.ResponseMessage;
import com.easymarket.easymarket.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;


    private PasswordEncoder encoder;

    private JwtProvider jwtProvider;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public ResponseEntity signUp(UserDto signUpForm) throws ResourceNotFoundException {

        if (userRepository.existsByUsername(signUpForm.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpForm.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = User.builder()
                .name(signUpForm.getName())
                .surname(signUpForm.getSurname())
                .username(signUpForm.getUsername())
                .email(signUpForm.getEmail())
                .password(encoder.encode(signUpForm.getPassword()))
                .role(signUpForm.getRole())
                .isBlock(signUpForm.getIsBlock())
                .build();
        userRepository.save(user);

        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);

    }

    @Override
    public ResponseEntity signIn(UserDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }
}
