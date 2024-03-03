package com.bankoperations.test.controller;

import com.bankoperations.test.dto.AccountDto;
import com.bankoperations.test.dto.TransferDto;
import com.bankoperations.test.facade.TransactionFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fertilizers/products")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionFacade transactionFacade;

    @PostMapping
    public AccountDto moneyTransfer(@Valid TransferDto transferDto){
        return transactionFacade.moneyTransfer(transferDto);
    }

}
