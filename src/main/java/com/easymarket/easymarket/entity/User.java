package com.easymarket.easymarket.entity;

import com.easymarket.easymarket.entity.enums.UserRole;

import java.util.Set;

public class User {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Set<UserRole> userRoleSet;
}
