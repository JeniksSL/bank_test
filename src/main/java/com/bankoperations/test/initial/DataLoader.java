package com.bankoperations.test.initial;


import com.bankoperations.test.dto.ContactDto;
import com.bankoperations.test.dto.RegistrationDto;
import com.bankoperations.test.dto.core.ContactType;
import com.bankoperations.test.facade.RegistrationFacade;
import com.bankoperations.test.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;

    private final RegistrationFacade registrationFacade;


    @Override
    public void run(ApplicationArguments args)  {
        if (userRepository.existsByLogin("test@gmail.com")) return;

        RegistrationDto registrationDto = RegistrationDto
                .builder()
                .login("test@gmail.com")
                .password("test@gmail.com")
                .initialDeposit(new BigDecimal("150.5"))
                .contacts(List.of(new ContactDto(null, "+51651881", ContactType.PHONE)))
                .build();
        registrationFacade.create(registrationDto);

    }
}
