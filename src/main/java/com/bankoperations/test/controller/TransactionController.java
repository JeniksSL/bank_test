package com.bankoperations.test.controller;

import com.bankoperations.test.dto.AccountDto;
import com.bankoperations.test.dto.TransferDto;
import com.bankoperations.test.facade.TransactionFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.bankoperations.test.controller.core.ControllerConst.API;

@RestController
@RequestMapping(API+"/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionFacade transactionFacade;

    @Operation(
            summary = "Perform transfer indicated money amount from current user's account to other",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Amount transferred", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDto.class))),
                    @ApiResponse(responseCode = "400", description = "Request body has invalid fields", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Amount is more than current user's balance", content = @Content(mediaType = "application/json"))
            }
    )
    @PostMapping
    public AccountDto moneyTransfer(@Valid @RequestBody TransferDto transferDto){
        return transactionFacade.moneyTransfer(transferDto);
    }

}
