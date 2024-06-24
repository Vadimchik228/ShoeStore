package com.vasche.shoestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasche.shoestore.service.UserService;
import com.vasche.shoestore.web.controller.ControllerAdvice;
import com.vasche.shoestore.web.controller.UserController;
import com.vasche.shoestore.web.dto.user.UserDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setControllerAdvice(new ControllerAdvice())
                .build();
    }

    @Test
    public void getByIdTest() throws Exception {
        UserDto userDto = new UserDto();
        Long userId = 1L;

        given(userService.getById(userId)).willReturn(userDto);

        mockMvc.perform(get("/api/v1/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(userDto.getName())));
    }

    @Test
    public void updateFailTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setUsername("johndoe@gmail.com");
        userDto.setPassword("12345");

        Mockito.lenient().when(userService.update(userDto)).thenReturn(userDto);

        mockMvc.perform(put("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void deleteByIdTest() throws Exception {
        Long userId = 1L;
        doNothing().when(userService).delete(userId);

        mockMvc.perform(delete("/api/v1/users/{id}", userId))
                .andExpect(status().isOk());

        verify(userService).delete(userId);
    }
}