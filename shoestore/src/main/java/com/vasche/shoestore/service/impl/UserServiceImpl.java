package com.vasche.shoestore.service.impl;

import com.vasche.shoestore.domain.exception.ResourceNotFoundException;
import com.vasche.shoestore.domain.user.Role;
import com.vasche.shoestore.domain.user.User;
import com.vasche.shoestore.repository.UserRepository;
import com.vasche.shoestore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.update(user);
        return user;
    }

    @Override
    @Transactional
    public User create(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("User already exists.");
        }
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation don't match.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.create(user);
        Set<Role> roles = Set.of(Role.ROLE_USER);
        userRepository.insertUserRole(user.getId(), Role.ROLE_USER);
        user.setRoles(roles);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isCartItemOwner(Long userId, Long cartItemId) {
        return userRepository.isCartItemOwner(userId, cartItemId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isOrderItemOwner(Long userId, Long orderItemId) {
        return userRepository.isOrderItemOwner(userId, orderItemId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isOrderOwner(Long userId, Long orderId) {
        return userRepository.isOrderOwner(userId, orderId);
    }


    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.delete(id);
    }
}
