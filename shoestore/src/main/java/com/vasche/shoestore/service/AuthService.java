package com.vasche.shoestore.service;

import com.vasche.shoestore.web.dto.auth.JwtRequest;
import com.vasche.shoestore.web.dto.auth.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);
}
