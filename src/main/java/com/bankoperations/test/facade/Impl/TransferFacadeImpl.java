package com.bankoperations.test.facade.Impl;

import com.bankoperations.test.domain.Account;
import com.bankoperations.test.dto.AccountDto;
import com.bankoperations.test.dto.TransferDto;
import com.bankoperations.test.facade.TransactionFacade;
import com.bankoperations.test.mapper.AccountMapper;
import com.bankoperations.test.service.AccountService;
import com.bankoperations.test.service.TransactionService;
import com.bankoperations.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferFacadeImpl implements TransactionFacade {
    private final TransactionService transactionService;
    private final UserService userService;
    private final AccountService accountService;

    private final AccountMapper accountMapper;


    @Override
    public AccountDto moneyTransfer(TransferDto transferDto) {
        Account currentUserAccount = accountService.getByUserId(userService.getCurrentUserId()).orElseThrow();
        Account acceptedUserAccount = accountService.getByUserId(transferDto.acceptorId()).orElseThrow();
        transactionService.performTransaction(currentUserAccount, transferDto.amount(), acceptedUserAccount);
        return accountMapper.toDto(accountService.getByUserId(userService.getCurrentUserId()).orElseThrow());
    }
}
