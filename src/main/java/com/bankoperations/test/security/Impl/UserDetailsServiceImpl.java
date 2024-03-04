package com.bankoperations.test.security.Impl;


import com.bankoperations.test.domain.User;
import com.bankoperations.test.repository.UserRepository;
import com.bankoperations.test.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByLogin(username).orElseThrow();
        return UserPrincipal.create(user);
    }
}
