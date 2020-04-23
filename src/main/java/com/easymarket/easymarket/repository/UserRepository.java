package com.easymarket.easymarket.repository;

import com.easymarket.easymarket.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Page<User> findAll(Pageable pageable);

    Optional<User> findById(Long id);

    User save(User user);

}

