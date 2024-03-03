package com.bankoperations.test.mapper.Impl;

import com.bankoperations.test.domain.Account;
import com.bankoperations.test.domain.User;
import com.bankoperations.test.dto.AccountDto;
import com.bankoperations.test.dto.RegistrationDto;
import com.bankoperations.test.mapper.AccountMapper;
import com.bankoperations.test.mapper.ContactMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMapperImpl implements AccountMapper {

    private final ModelMapper mapper;
    private final ContactMapper contactMapper;


    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Account.class, AccountDto.class)
                .addMappings(m -> m.skip(AccountDto::setContacts))
                .addMappings(m->m.skip(AccountDto::setBirthDate))
                .setPostConverter(context -> {
                    Account source = context.getSource();
                    AccountDto destination = context.getDestination();
                    destination.setContacts(source.getContacts().stream().map(contactMapper::toDto).toList());
                    destination.setBirthDate(source.getBirthDate().toString());
                    return context.getDestination();
                });
        mapper.createTypeMap(RegistrationDto.class, Account.class)
                .addMappings(m -> m.skip(Account::setContacts))
                .addMappings(m -> m.skip(Account::setBalance))
                .setPostConverter(context -> {
                    RegistrationDto source = context.getSource();
                    Account destination = context.getDestination();
                    destination.setContacts(source.contacts().stream().map(contactMapper::toEntity).toList());
                    destination.setBalance(source.initialDeposit());
                    return context.getDestination();
                });
    }

    @Override
    public AccountDto toDto(Account account) {
        return mapper.map(account, AccountDto.class);
    }

    @Override
    public Account toEntity(RegistrationDto registrationDto) {
        return mapper.map(registrationDto, Account.class);
    }

}
