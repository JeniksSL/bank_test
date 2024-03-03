package com.bankoperations.test.mapper;

import com.bankoperations.test.domain.User;
import com.bankoperations.test.dto.RegistrationDto;

public interface UserMapper {

    User toEntity(RegistrationDto registrationDto);
}
