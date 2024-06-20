package com.vasche.shoestore.service.impl;

import com.vasche.shoestore.domain.exception.ResourceNotFoundException;
import com.vasche.shoestore.domain.mail.MailType;
import com.vasche.shoestore.domain.user.Role;
import com.vasche.shoestore.domain.user.User;
import com.vasche.shoestore.repository.UserRepository;
import com.vasche.shoestore.service.MailService;
import com.vasche.shoestore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Properties;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailService mailService;

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(value = "UserService::getById", key = "#id")
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(value = "UserService::getByUsername", key = "#username")
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional
//    @Caching(
//            put = {
//                    @CachePut(value = "UserService::getById", key = "user.id"),
//                    @CachePut(value = "UserService::getByUsername", key = "user.username")
//            }
//    )
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
//    @Caching(
//            cacheable = {
//                    @Cacheable(value = "UserService::getById", key = "user.id"),
//                    @Cacheable(value = "UserService::getByUsername", key = "user.username")
//            }
//    )
    public User create(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("User already exists.");
        }
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation don't match.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = Set.of(Role.ROLE_USER);
        user.setRoles(roles);
        userRepository.save(user);
        mailService.sendEmail(user, MailType.REGISTRATION, new Properties());
        return user;
    }

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(value = "UserService::isCartItemOwner", key = "#userId" + '.' + "#cartItemId")
    public boolean isCartItemOwner(Long userId, Long cartItemId) {
        return userRepository.isCartItemOwner(userId, cartItemId);
    }

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(value = "UserService::isOrderItemOwner", key = "#userId" + '.' + "#orderItemId")
    public boolean isOrderItemOwner(Long userId, Long orderItemId) {
        return userRepository.isOrderItemOwner(userId, orderItemId);
    }

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(value = "UserService::isOrderOwner", key = "#userId" + '.' + "#orderId")
    public boolean isOrderOwner(Long userId, Long orderId) {
        return userRepository.isOrderOwner(userId, orderId);
    }

    @Override
    @Transactional
//    @CacheEvict(value = "UserService::getById", key = "#id")
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
