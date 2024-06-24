package com.vasche.shoestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasche.shoestore.service.AuthService;
import com.vasche.shoestore.web.controller.AuthController;
import com.vasche.shoestore.web.dto.auth.JwtRequest;
import com.vasche.shoestore.web.dto.auth.JwtResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @org.junit.jupiter.api.BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(authController)
                .build();
    }

    @Test
    void testLogin() throws Exception {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("test@example.com");
        jwtRequest.setPassword("password");

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setId(1L);
        jwtResponse.setUsername("test@example.com");
        jwtResponse.setAccessToken("access-token");
        jwtResponse.setRefreshToken("refresh-token");

        when(authService.login(jwtRequest)).thenReturn(jwtResponse);

        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jwtRequest)));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("test@example.com"))
                .andExpect(jsonPath("$.accessToken").value("access-token"))
                .andExpect(jsonPath("$.refreshToken").value("refresh-token"));

        verify(authService).login(jwtRequest);
    }

    @Test
    void testRefresh() throws Exception {
        String refreshToken = "refresh-token";

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setId(1L);
        jwtResponse.setUsername("test@example.com");
        jwtResponse.setAccessToken("access-token");
        jwtResponse.setRefreshToken("refresh-token");

        when(authService.refresh(refreshToken)).thenReturn(jwtResponse);

        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(refreshToken));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("test@example.com"))
                .andExpect(jsonPath("$.accessToken").value("access-token"))
                .andExpect(jsonPath("$.refreshToken").value("refresh-token"));

        verify(authService).refresh(refreshToken);
    }
}
