package com.bankoperations.test.controller;

import com.bankoperations.test.dto.AccountDto;
import com.bankoperations.test.dto.RegistrationDto;
import com.bankoperations.test.facade.RegistrationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("fertilizers/products")
@RequiredArgsConstructor
public class RegistrationController {

private final RegistrationFacade accountFacade;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    AccountDto resolveCase(@RequestBody RegistrationDto registrationDto){
        return accountFacade.create(registrationDto);
    }



}
