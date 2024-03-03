package com.bankoperations.test.service.Impl;

import com.bankoperations.test.domain.Account;
import com.bankoperations.test.repository.AccountRepository;
import com.bankoperations.test.service.AccountService;
import com.bankoperations.test.service.TransactionService;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    @Override
    @Transactional
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    public void performTransaction(Account currentUserAccount, BigDecimal amount, Account acceptedUserAccount) {
        validateAmount(currentUserAccount, amount);
        BigDecimal currentUserNewBalance = currentUserAccount.getBalance().subtract(amount);

        BigDecimal acceptedUserNewBalance = acceptedUserAccount.getBalance().add(amount);


    }

    private void validateAmount(final Account currentUserAccount, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO)<=0) throw new RuntimeException();
        if (currentUserAccount.getBalance().compareTo(amount)<=0) throw new RuntimeException();
    }
}
