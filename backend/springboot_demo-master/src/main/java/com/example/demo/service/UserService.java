package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.dto.UserDto;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);

    UserDto getUser(String userEmail);

    UserDto getUserByUserId(String id);

    UserDto updateUser(String id, UserDto userDto);
    
    void deleteUser(String id);

}
