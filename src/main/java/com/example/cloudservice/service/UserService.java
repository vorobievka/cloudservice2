package com.example.cloudservice.service;

import com.example.cloudservice.dto.AuthRequestDto;
import com.example.cloudservice.dto.AuthResponseDto;

public interface UserService {

    AuthResponseDto loginUser(AuthRequestDto authRequest);

    void logoutUser(String authToken);
}