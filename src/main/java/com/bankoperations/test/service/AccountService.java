package com.bankoperations.test.service;

import com.bankoperations.test.domain.Account;
import com.bankoperations.test.domain.Contact;
import com.bankoperations.test.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface AccountService {
    Account create(Account account, User user);

    Page<Account> getAllAccounts(Specification<Account> specification, Pageable pageable);

    Contact createContact(Long currentUserId, Contact contact);

    void deleteContact(Long currentUserId, Long contactId);

    Contact replaceContact(Long currentUserId, Long contactId, Contact contact);

    Optional<Account> getByUserId(Long currentUserId);
}
