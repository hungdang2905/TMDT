package com.example.demo.service;

import com.example.demo.dto.request.UserLoginDto;
import com.example.demo.dto.request.UserRegisterDto;

public interface AuthService {

    UserRegisterDto register(UserRegisterDto userRegisterDto);
    String login(UserLoginDto userLoginDto);

}
