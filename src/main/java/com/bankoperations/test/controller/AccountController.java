package com.bankoperations.test.controller;

import com.bankoperations.test.dto.AccSearchRequestDto;
import com.bankoperations.test.dto.AccountDto;
import com.bankoperations.test.dto.ContactDto;
import com.bankoperations.test.dto.TransferDto;
import com.bankoperations.test.dto.core.PageDto;
import com.bankoperations.test.facade.AccountFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("fertilizers/products")
@RequiredArgsConstructor
public class AccountController {

    private final AccountFacade accountFacade;

    @GetMapping()
    @Operation(
            summary = "Get tasks by criteria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tasks received", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "422", description = "Request body has invalid fields", content = @Content(mediaType = "application/json"))
            }
    )
    public PageDto<AccountDto> getAllAccounts(@Valid AccSearchRequestDto searchRequestDto) {
        return accountFacade.getAllAccounts(searchRequestDto);
    }


    @PostMapping("/contact")
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDto createContact(@Valid ContactDto contactDto){
        return accountFacade.createContact(contactDto);
    }

    @PutMapping("/contact/{id}")
    public ContactDto replaceContact(@PathVariable Long id, @Valid ContactDto contactDto){
        return accountFacade.replaceContact(id, contactDto);
    }

    @DeleteMapping("/contact/{id}")
    public void deleteContact(@PathVariable Long id){
        accountFacade.deleteContact(id);
    }


}
