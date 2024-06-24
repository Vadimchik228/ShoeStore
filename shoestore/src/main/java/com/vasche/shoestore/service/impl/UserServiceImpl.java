package com.vasche.shoestore.service.impl;

import com.vasche.shoestore.domain.exception.ResourceNotFoundException;
import com.vasche.shoestore.domain.mail.MailType;
import com.vasche.shoestore.domain.user.Role;
import com.vasche.shoestore.domain.user.User;
import com.vasche.shoestore.repository.UserRepository;
import com.vasche.shoestore.service.MailService;
import com.vasche.shoestore.service.UserService;
import com.vasche.shoestore.web.dto.user.UserDto;
import com.vasche.shoestore.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Properties;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "ShoeService::getById", key = "#id")
    public UserDto getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return userMapper.toDto(user.get());
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::getByUsername", key = "#username")
    public UserDto getByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return userMapper.toDto(user.get());
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }

    @Override
    @Transactional
    @Caching(
            put = {
                    @CachePut(value = "UserService::getById", key = "#result.id", condition = "#result != null"),
                    @CachePut(value = "UserService::getByUsername", key = "#result.username", condition = "#result != null")
            }
    )
    public UserDto update(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userDto;
    }

    @Override
    @Transactional
    @Caching(
            cacheable = {
                    @Cacheable(value = "UserService::getById", key = "#result.id", condition = "#result != null"),
                    @Cacheable(value = "UserService::getByUsername", key = "#result.username", condition = "#result != null")
            }
    )
    public UserDto create(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new IllegalStateException("User already exists.");
        }
        if (!userDto.getPassword().equals(userDto.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation don't match.");
        }
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = Set.of(Role.ROLE_USER);
        user.setRoles(roles);
        userRepository.save(user);
        mailService.sendEmail(user, MailType.REGISTRATION, new Properties());
        userDto.setId(user.getId());
        return userDto;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::isCartItemOwner", key = "#userId" + '.' + "#cartItemId")
    public boolean isCartItemOwner(Long userId, Long cartItemId) {
        return userRepository.isCartItemOwner(userId, cartItemId);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::isOrderItemOwner", key = "#userId" + '.' + "#orderItemId")
    public boolean isOrderItemOwner(Long userId, Long orderItemId) {
        return userRepository.isOrderItemOwner(userId, orderItemId);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::isOrderOwner", key = "#userId" + '.' + "#orderId")
    public boolean isOrderOwner(Long userId, Long orderId) {
        return userRepository.isOrderOwner(userId, orderId);
    }

    @Override
    @Transactional
    @CacheEvict(value = "UserService::getById", key = "#id")
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
