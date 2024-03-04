package com.bankoperations.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferDto(
        @NotNull
        @Schema(description = "Deposit acceptor id", example = "14")
        Long acceptorAccountId,

        @Schema(description = "Amount to transfer", example = "50.85")
        @DecimalMin(value = "0.0", message = "The value must be positive")
        BigDecimal amount
) {

}
