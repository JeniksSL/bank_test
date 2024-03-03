package com.bankoperations.test.mapper.Impl;

import com.bankoperations.test.domain.User;
import com.bankoperations.test.dto.RegistrationDto;
import com.bankoperations.test.mapper.UserMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    private final ModelMapper mapper;
    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(RegistrationDto.class, User.class);
    }

    @Override
    public User toEntity(RegistrationDto registrationDto) {
        return mapper.map(registrationDto, User.class);
    }

}
