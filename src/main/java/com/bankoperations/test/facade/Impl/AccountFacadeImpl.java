package com.bankoperations.test.facade.Impl;

import com.bankoperations.test.domain.Account;
import com.bankoperations.test.domain.Contact;
import com.bankoperations.test.domain.User;
import com.bankoperations.test.dto.*;
import com.bankoperations.test.dto.core.PageDto;
import com.bankoperations.test.facade.AccountFacade;
import com.bankoperations.test.facade.RegistrationFacade;
import com.bankoperations.test.mapper.AccountMapper;
import com.bankoperations.test.mapper.ContactMapper;
import com.bankoperations.test.mapper.UserMapper;
import com.bankoperations.test.service.AccountService;
import com.bankoperations.test.service.UserService;
import com.bankoperations.test.specification.AccountSpecificationBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountFacadeImpl implements AccountFacade, RegistrationFacade {

    private final AccountService accountService;
    private final UserService userService;
    private final AccountMapper accountMapper;
    private final UserMapper userMapper;

    private final ContactMapper contactMapper;

    private final AccountSpecificationBuilder accountSpecificationBuilder;
    @Override
    public PageDto<AccountDto> getAllAccounts(AccSearchRequestDto searchRequestDto) {
        Specification<Account> specification = accountSpecificationBuilder.buildSpecification(searchRequestDto);
        Pageable pageable = accountSpecificationBuilder.buildPageable(searchRequestDto);
        Page<Account> accountPage = accountService.getAllAccounts(specification, pageable);
        return new PageDto<>(accountPage.stream().map(accountMapper::toDto).toList(),
                accountPage.getTotalPages(),
                accountPage.getTotalElements());
    }

    @Override
    public ContactDto createContact(ContactDto contactDto) {
        Contact created = accountService
                .createContact(userService.getCurrentUserId(), contactMapper.toEntity(contactDto));
        return contactMapper.toDto(created);
    }

    @Override
    public void deleteContact(Long  contactId) {
        accountService.deleteContact(userService.getCurrentUserId(),  contactId);
    }

    @Override
    public ContactDto replaceContact(Long  contactId, ContactDto contactDto) {
        Contact replaced = accountService
                .replaceContact(userService.getCurrentUserId(),  contactId, contactMapper.toEntity(contactDto));
        return contactMapper.toDto(replaced);
    }

    @Override
    @Transactional
    public AccountDto create(RegistrationDto registrationDto) {
        User user = userService.create(userMapper.toEntity(registrationDto));
        Account account = accountService.create(accountMapper.toEntity(registrationDto), user);
        return accountMapper.toDto(account);
    }
}
