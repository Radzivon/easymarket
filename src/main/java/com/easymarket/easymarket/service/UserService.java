package com.easymarket.easymarket.service;

import com.easymarket.easymarket.entity.User;
import com.easymarket.easymarket.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> getAll(Pageable pageable);
    User getById(Long id) throws ResourceNotFoundException;
    void save(User user);
    void update(User user);
    void block(User user, boolean isBlock);
}
