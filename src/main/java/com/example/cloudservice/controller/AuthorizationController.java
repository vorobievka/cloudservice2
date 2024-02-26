package com.example.cloudservice.controller;

import com.example.cloudservice.dto.AuthRequestDto;
import com.example.cloudservice.dto.AuthResponseDto;
import com.example.cloudservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.cloudservice.controller.PathConstant.LOGIN;
import static com.example.cloudservice.controller.PathConstant.LOGOUT;

@RestController
@RequiredArgsConstructor
public class AuthorizationController {

    private final UserService userService;

    @PostMapping(LOGIN)
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequest) {
        AuthResponseDto authResponse = userService.loginUser(authRequest);
        return ResponseEntity.ok(authResponse);

    }

    @PostMapping(LOGOUT)
    public ResponseEntity<Void> logout(@RequestHeader(value = "auth-token", required = false) String authToken) {
        userService.logoutUser(authToken);
        return ResponseEntity.ok().build();
    }
}