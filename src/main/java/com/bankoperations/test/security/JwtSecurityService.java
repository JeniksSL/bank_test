package com.bankoperations.test.security;

import com.bankoperations.test.dto.LoginRequest;
import com.bankoperations.test.dto.TokenResponse;

public interface JwtSecurityService {
    TokenResponse login(LoginRequest request);

    TokenResponse refresh();
}

