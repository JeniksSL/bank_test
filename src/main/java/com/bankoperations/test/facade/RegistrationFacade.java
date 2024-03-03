package com.bankoperations.test.facade;

import com.bankoperations.test.dto.AccountDto;
import com.bankoperations.test.dto.RegistrationDto;

public interface RegistrationFacade {
    AccountDto create(RegistrationDto registrationDto);

}
