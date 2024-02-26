package com.example.cloudservice.service;

import com.example.cloudservice.dto.AuthTokenDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.example.cloudservice.controller.PathConstant.LOGIN;
import static com.example.cloudservice.service.PrepareInfoForTest.NON_VALID_TEST;
import static com.example.cloudservice.service.PrepareInfoForTest.TEST;
import static com.example.cloudservice.service.PrepareInfoForTest.TOKEN;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    public final static String LOGIN_NOT_VALID_PASSWORD = "The password is not correct!";

    public final static String TOKEN_INVALID = "Invalid token!";


    @Test
    public void loginValidTesta() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\": \"" + TEST + "\", \"password\": \"" + TEST + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.auth-token").isNotEmpty());
    }

    @Test
    public void loginNonValidTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\": \"" + TEST + "\", \"password\": \"" + NON_VALID_TEST + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(MockMvcResultMatchers.content().string(LOGIN_NOT_VALID_PASSWORD));
    }
}