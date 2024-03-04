package com.bankoperations.test.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RegistrationDto{

        private String login;
        private String password;
        private BigDecimal initialDeposit;
        @NotEmpty
        private List<@Valid ContactDto> contacts;
}
