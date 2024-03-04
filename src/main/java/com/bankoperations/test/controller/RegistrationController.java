package com.bankoperations.test.controller;

import com.bankoperations.test.dto.AccountDto;
import com.bankoperations.test.dto.RegistrationDto;
import com.bankoperations.test.facade.RegistrationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.bankoperations.test.controller.core.ControllerConst.API;

@RestController
@RequestMapping(API+"/registration")
@RequiredArgsConstructor
public class RegistrationController {

private final RegistrationFacade accountFacade;

    @PostMapping("/account")
    @ResponseStatus(HttpStatus.CREATED)
    AccountDto registerAccount(@RequestBody RegistrationDto registrationDto){
        return accountFacade.create(registrationDto);
    }



}
