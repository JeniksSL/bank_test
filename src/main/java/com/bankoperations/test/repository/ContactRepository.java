package com.bankoperations.test.repository;

import com.bankoperations.test.domain.Contact;
import com.bankoperations.test.dto.core.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findByContactAndContact_type(String contact, ContactType contactType);


    List<Contact> findByUserId(Long currentUserId);
}
