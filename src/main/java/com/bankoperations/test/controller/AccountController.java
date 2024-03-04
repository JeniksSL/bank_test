package com.bankoperations.test.controller;

import com.bankoperations.test.dto.AccSearchRequestDto;
import com.bankoperations.test.dto.AccountDto;
import com.bankoperations.test.dto.ContactDto;
import com.bankoperations.test.dto.core.PageDto;
import com.bankoperations.test.facade.AccountFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.bankoperations.test.controller.core.ControllerConst.API;

@RestController
@RequestMapping(API+"/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountFacade accountFacade;

    @GetMapping()
    public PageDto<AccountDto> getAllAccounts(@Valid @RequestBody AccSearchRequestDto searchRequestDto) {
        return accountFacade.getAllAccounts(searchRequestDto);
    }


    @PostMapping("/contact")
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDto createContact(@Valid @RequestBody ContactDto contactDto){
        return accountFacade.createContact(contactDto);
    }

    @PutMapping("/contact/{id}")
    public ContactDto replaceContact(@PathVariable Long id, @Valid @RequestBody ContactDto contactDto){
        return accountFacade.replaceContact(id, contactDto);
    }

    @DeleteMapping("/contact/{id}")
    public void deleteContact(@PathVariable Long id){
        accountFacade.deleteContact(id);
    }


}
