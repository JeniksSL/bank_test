package com.bankoperations.test.security.Impl;

import com.bankoperations.test.domain.User;
import com.bankoperations.test.dto.LoginRequest;
import com.bankoperations.test.dto.TokenResponse;
import com.bankoperations.test.properties.JwtProperties;
import com.bankoperations.test.repository.UserRepository;
import com.bankoperations.test.security.JwtSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class JwtSecurityServiceImpl implements JwtSecurityService {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtProperties jwtProperties;

    @Override
    public TokenResponse login(LoginRequest request) {
        User user = userRepository.getUserByLogin(request.username()).orElseThrow();
        if (passwordEncoder.matches(request.password(), user.getPassword())) {
            Long userId = user.getId();
            return new TokenResponse(generateAccessToken(userId), generateRefreshToken(userId));
        }
        throw new RuntimeException();
    }

    @Override
    public TokenResponse refresh() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Jwt jwt) {
            String subject = jwt.getSubject();
            Long userId = Long.valueOf(subject);
            return new TokenResponse(generateAccessToken(userId),generateRefreshToken(userId));
        }
        throw new RuntimeException();
    }


    private String generateAccessToken(Long userId) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(jwtProperties.getAccessTokenMinutes(), ChronoUnit.MINUTES))
                .subject(userId.toString())
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private String generateRefreshToken(Long userId) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(jwtProperties.getRefreshTokenDays(), ChronoUnit.DAYS))
                .subject(userId.toString())
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
