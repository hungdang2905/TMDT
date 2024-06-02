package com.example.demo.service;

import com.example.demo.dto.request.UserGetAllDto;
import com.example.demo.dto.request.UserGetByIdDto;
import com.example.demo.dto.request.UserUpdateByIdDto;

import java.util.List;

public interface UserService {

    List<UserGetAllDto> getAllUsers();

    UserGetByIdDto getUserById(Long userId, String currentUsername);

    UserUpdateByIdDto updateUserById(Long userId);

}

