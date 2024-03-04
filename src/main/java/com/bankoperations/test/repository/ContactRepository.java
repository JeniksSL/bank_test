package com.bankoperations.test.repository;

import com.bankoperations.test.domain.Contact;
import com.bankoperations.test.dto.core.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findByContactAndContactType(String contact, ContactType contactType);
}
