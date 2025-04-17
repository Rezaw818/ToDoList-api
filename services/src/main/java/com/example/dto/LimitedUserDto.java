package com.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LimitedUserDto {

    private Long id;

    private String username;

    private String email;

    private String password;

    private String token;
}
