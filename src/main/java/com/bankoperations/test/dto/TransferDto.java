package com.bankoperations.test.dto;

import java.math.BigDecimal;

public record TransferDto(
        Long acceptorId,
        BigDecimal amount
) {

}
