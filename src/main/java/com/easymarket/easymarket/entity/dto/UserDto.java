package com.easymarket.easymarket.entity.dto;

import com.easymarket.easymarket.entity.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Role role;
    private Boolean isBlock;
}
