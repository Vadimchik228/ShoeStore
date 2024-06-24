package com.vasche.shoestore.web.security;

import com.vasche.shoestore.domain.user.User;
import com.vasche.shoestore.repository.UserRepository;
import com.vasche.shoestore.service.UserService;
import com.vasche.shoestore.web.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(JwtEntityFactory::create).orElse(null);
    }

}
