package com.example.cloudservice.service;

import com.example.cloudservice.exceptions.LoginException;
import com.example.cloudservice.dto.AuthRequestDto;
import com.example.cloudservice.dto.AuthResponseDto;
import com.example.cloudservice.model.entity.TokenEntity;
import com.example.cloudservice.model.entity.UserEntity;
import com.example.cloudservice.repository.TokenRepository;
import com.example.cloudservice.repository.UserRepository;
import com.example.cloudservice.security.JwtTokenUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Data
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private  final TokenRepository tokenRepository;

    private final JwtTokenUtils authToken;

    public final static String LOGIN_NON_VALID_VALUE = "Login or password cannot be empty!";
    public final static String LOGIN_NOT_FOUND_USER = "A user with this username was not found!";
    public final static String LOGIN_NOT_VALID_PASSWORD = "The password is not correct!";


    @Override
    public AuthResponseDto loginUser(AuthRequestDto authRequest) {
        if (isNull(authRequest.getLogin()) && isNull(authRequest.getPassword())) {
            throw new LoginException(LOGIN_NON_VALID_VALUE);
        }
        UserEntity userEntity = userRepository.getUsersByEmail(authRequest.getLogin()).orElseThrow(()
                -> new LoginException(LOGIN_NOT_FOUND_USER));
        if (!authRequest.getPassword().equals(userEntity.getPassword())) {
            throw new LoginException(LOGIN_NOT_VALID_PASSWORD);
        }

        String token = authToken.generateToken(userEntity);
        saveAuthUser(token,userEntity);

        return new AuthResponseDto(token);
    }

    private void saveAuthUser(String token,UserEntity user){
        TokenEntity tokenEntity = new TokenEntity(token, user);
        tokenRepository.save(tokenEntity);
        user.setToken(tokenEntity);
        userRepository.save(user);
    }

    @Override
    public void logoutUser(String authToken) {
        userRepository.getUsersByEmail(authToken);
        log.info("User logout");
        userRepository.removeAllByEmail(authToken);
    }
}