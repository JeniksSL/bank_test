package com.bankoperations.test.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.List;

public record RegistrationDto(
        String login,
        String password,
        BigDecimal initialDeposit,
        @NotEmpty
        List<@Valid ContactDto> contacts
) {

}
