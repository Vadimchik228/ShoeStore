package com.vasche.shoestore.service.impl;

import com.vasche.shoestore.domain.exception.ResourceNotFoundException;
import com.vasche.shoestore.domain.mail.MailType;
import com.vasche.shoestore.domain.user.User;
import com.vasche.shoestore.repository.UserRepository;
import com.vasche.shoestore.service.MailService;
import com.vasche.shoestore.web.dto.user.UserDto;
import com.vasche.shoestore.web.mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MailService mailService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Vadim");
        userDto.setUsername("schebet@gmail.com");
        userDto.setPassword("12345");
        userDto.setPasswordConfirmation("12345");

        user = User.builder()
                .id(1L)
                .name("Vadim")
                .username("schebet@gmail.com")
                .password("12345")
                .build();
    }

    @Test
    void getByIdTest() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto actualUserDto = userService.getById(1L);

        assertThat(actualUserDto).isEqualTo(userDto);
        verify(userRepository).findById(1L);
        verify(userMapper).toDto(user);
    }

    @Test
    void getByIdNotFoundTest() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getById(1L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getByUsernameTest() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto actualUserDto = userService.getByUsername("schebet@gmail.com");

        assertThat(actualUserDto).isEqualTo(userDto);
        verify(userRepository).findByUsername("schebet@gmail.com");
        verify(userMapper).toDto(user);
    }

    @Test
    void getByUsernameNotFoundTest() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getByUsername("schebet@gmail.com")).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updateTest() {
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        UserDto actualUserDto = userService.update(userDto);

        assertThat(actualUserDto).isEqualTo(userDto);
        verify(userMapper).toEntity(userDto);
        verify(passwordEncoder).encode(userDto.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void createTest() {
        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Optional.empty());
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        UserDto actualUserDto = userService.create(userDto);

        assertThat(actualUserDto).isEqualTo(userDto);
        verify(userRepository).findByUsername(userDto.getUsername());
        verify(userMapper).toEntity(userDto);
        verify(passwordEncoder).encode(userDto.getPassword());
        verify(userRepository).save(user);

        verify(mailService).sendEmail(eq(user), eq(MailType.REGISTRATION), any(Properties.class));
    }

    @Test
    void createExistingUserTest() {
        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.create(userDto)).isInstanceOf(IllegalStateException.class)
                .hasMessage("User already exists.");
        verify(userRepository).findByUsername(userDto.getUsername());
    }

    @Test
    void createPasswordMismatchTest() {
        userDto.setPasswordConfirmation("123456");
        assertThatThrownBy(() -> userService.create(userDto)).isInstanceOf(IllegalStateException.class)
                .hasMessage("Password and password confirmation don't match.");
    }

}