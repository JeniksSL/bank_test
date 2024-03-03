package com.bankoperations.test.facade;

import com.bankoperations.test.dto.AccountDto;
import com.bankoperations.test.dto.TransferDto;

public interface TransactionFacade {
    AccountDto moneyTransfer(TransferDto transferDto);
}
