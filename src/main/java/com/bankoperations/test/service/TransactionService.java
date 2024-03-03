package com.bankoperations.test.service;

import com.bankoperations.test.domain.Account;
import com.bankoperations.test.repository.AccountRepository;

import java.math.BigDecimal;

public interface TransactionService {
    void performTransaction(Account currentUserAccount, BigDecimal amount, Account acceptedUserAccount);
}
