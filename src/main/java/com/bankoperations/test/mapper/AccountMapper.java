package com.bankoperations.test.mapper;

import com.bankoperations.test.domain.Account;
import com.bankoperations.test.domain.Contact;
import com.bankoperations.test.domain.User;
import com.bankoperations.test.dto.AccountDto;
import com.bankoperations.test.dto.ContactDto;
import com.bankoperations.test.dto.RegistrationDto;

public interface AccountMapper {
    AccountDto toDto(Account account);
    Account toEntity(RegistrationDto registrationDto);

}
