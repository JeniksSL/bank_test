package com.bankoperations.test.mapper.Impl;

import com.bankoperations.test.domain.Contact;
import com.bankoperations.test.dto.ContactDto;
import com.bankoperations.test.dto.core.ContactType;
import com.bankoperations.test.mapper.ContactMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ContactMapperImpl implements ContactMapper {

    @Override
    public ContactDto toDto(Contact contact) {
        Objects.requireNonNull(contact);
        if (contact.getContactType().equals(ContactType.PHONE))  return new ContactDto(contact.getId(), contact.getContact(), ContactType.PHONE);
        else if (contact.getContactType().equals(ContactType.EMAIL)) return new ContactDto(contact.getId(), contact.getContact(), ContactType.EMAIL);
        throw new RuntimeException();
    }

    @Override
    public Contact toEntity(ContactDto contactDto) {
        Objects.requireNonNull(contactDto);
        return Contact.builder().contactType(contactDto.type()).contact(contactDto.contact()).build();
    }
}
