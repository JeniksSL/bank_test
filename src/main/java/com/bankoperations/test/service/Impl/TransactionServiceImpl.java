package com.bankoperations.test.service.Impl;

import com.bankoperations.test.domain.Account;
import com.bankoperations.test.repository.AccountRepository;
import com.bankoperations.test.service.TransactionService;
import com.bankoperations.test.service.core.BalanceOperationType;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    @Override
    @Transactional
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    public void performTransaction(Long currentUserId, BigDecimal amount, Long acceptedAccountId) {
        Account currentUserAccount = accountRepository
                .findByUserId(currentUserId)
                .orElseThrow(()->{
                    log.error(String.format("Current user's account not fount, user's id is %d", currentUserId));
                    throw new RuntimeException();
                });
        Account acceptedAccount = accountRepository
                .findById(acceptedAccountId)
                .orElseThrow(()->{
                    log.error(String.format("Acceptor's account not fount, id is %d", acceptedAccountId));
                    throw new RuntimeException();
                });
        validateAmount(currentUserAccount, amount);
        changeAccountBalance(currentUserAccount, amount, BalanceOperationType.SUBTRACT);
        changeAccountBalance(acceptedAccount, amount, BalanceOperationType.ADD);
    }

    private void changeAccountBalance(Account account, BigDecimal amount, BalanceOperationType balanceOperationType) {
       BigDecimal newBalance = account.getBalance();
       switch (balanceOperationType) {
           case ADD -> newBalance = newBalance.add(amount);
           case SUBTRACT -> newBalance = newBalance.subtract(amount);
       }
       account.setBalance(newBalance);
       validateAccountBalance(account);
       accountRepository.save(account);
    }

    private void validateAccountBalance(Account account) {
        if (account.getBalance().compareTo(BigDecimal.ZERO)<=0) {
            log.error(String.format("Resulting balance is negative: %s", account.getBalance()));
            throw new RuntimeException();
        }
    }

    private void validateAmount(final Account currentUserAccount, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO)<=0) {
            log.error(String.format("Amount to transfer is %s and negative", amount));
            throw new RuntimeException();
        }
        if (currentUserAccount.getBalance().compareTo(amount)<=0) {
            log.error(String.format("Amount to transfer is more than current balance, amount: %s, balance: %s", amount, currentUserAccount.getBalance()));
            throw new RuntimeException();
        }
    }
}
