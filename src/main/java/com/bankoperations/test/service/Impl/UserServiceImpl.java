package com.bankoperations.test.service.Impl;

import com.bankoperations.test.domain.User;
import com.bankoperations.test.repository.UserRepository;
import com.bankoperations.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {
        if (userRepository.existsByLogin(user.getLogin())) throw new RuntimeException();
        completeUserFields(user);
        return userRepository.save(user);
    }

    private void completeUserFields(User user) {
        user.setIsEnabled(true);
        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    @Override
    public Long getCurrentUserId() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Jwt jwt) {
            String subject = jwt.getSubject();
            return Long.valueOf(subject);
        }
        throw new RuntimeException();
    }
}
