package com.vasche.shoestore.service.impl;

import com.vasche.shoestore.domain.user.User;
import com.vasche.shoestore.repository.UserRepository;
import com.vasche.shoestore.service.AuthService;
import com.vasche.shoestore.web.dto.auth.JwtRequest;
import com.vasche.shoestore.web.dto.auth.JwtResponse;
import com.vasche.shoestore.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword())
        );
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        if (user.isPresent()) {
            jwtResponse.setId(user.get().getId());
            jwtResponse.setUsername(user.get().getUsername());
            jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(
                    user.get().getId(), user.get().getUsername(), user.get().getRoles())
            );
            jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(
                    user.get().getId(), user.get().getUsername())
            );
            return jwtResponse;
        }
        return null;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}
