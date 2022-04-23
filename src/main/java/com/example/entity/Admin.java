package com.example.entity;

import lombok.Data;

@Data
public class Admin {
    private Long id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String avatar;
}
