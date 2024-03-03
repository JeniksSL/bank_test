package com.bankoperations.test.mapper;

import com.bankoperations.test.domain.Contact;
import com.bankoperations.test.dto.ContactDto;

public interface ContactMapper {
    ContactDto toDto(Contact contact);
   Contact toEntity(ContactDto contactDto);
}
