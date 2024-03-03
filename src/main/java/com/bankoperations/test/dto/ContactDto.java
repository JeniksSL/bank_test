package com.bankoperations.test.dto;

import com.bankoperations.test.dto.core.ContactType;

public record ContactDto(
        Long id,
        String contact,
        ContactType type
) {
}
