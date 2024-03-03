package com.bankoperations.test.dto;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String name;
    private String birthDate;
    private BigDecimal initialDeposit;
    private BigDecimal balance;
    private List<ContactDto> contacts;
}
