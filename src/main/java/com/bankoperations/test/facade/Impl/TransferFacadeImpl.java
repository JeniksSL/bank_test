package com.bankoperations.test.facade.Impl;

import com.bankoperations.test.dto.AccountDto;
import com.bankoperations.test.dto.TransferDto;
import com.bankoperations.test.facade.TransactionFacade;
import com.bankoperations.test.mapper.AccountMapper;
import com.bankoperations.test.service.AccountService;
import com.bankoperations.test.service.TransactionService;
import com.bankoperations.test.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferFacadeImpl implements TransactionFacade {
    private final TransactionService transactionService;
    private final UserService userService;
    private final AccountService accountService;
    private final AccountMapper accountMapper;


    @Override
    public AccountDto moneyTransfer(TransferDto transferDto) {
        Long currentUserId = userService.getCurrentUserId();
        transactionService.performTransaction(currentUserId, transferDto.amount(), transferDto.acceptorAccountId());
        return accountMapper.toDto(accountService.getByUserId(currentUserId).orElseThrow(()->{
            log.error(String.format("Current user's account not fount, user's id is %d", currentUserId));
            throw new RuntimeException();
        }));
    }
}
