package com.bankoperations.test.dto;

import com.bankoperations.test.dto.core.AccountOrder;
import jakarta.validation.constraints.NotNull;

public record AccSearchRequestDto(
        String birthDate,
        String name,
        String phone,
        String email,
        AccountOrder accountOrder,
        Boolean isDesc,
        @NotNull Integer page,
        @NotNull Integer size
) {





}
