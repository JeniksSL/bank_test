package com.bankoperations.test.service.Impl;

import com.bankoperations.test.domain.Account;
import com.bankoperations.test.domain.Contact;
import com.bankoperations.test.domain.User;
import com.bankoperations.test.repository.AccountRepository;
import com.bankoperations.test.repository.ContactRepository;
import com.bankoperations.test.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final ContactRepository contactRepository;

    @Value("${account.contacts.minValue}")
    Integer minContactsAmount;
    @Override
    @Transactional
    public Account create(Account account, User user) {
        List<Contact> existed = account
                .getContacts()
                .stream()
                .map(it->contactRepository.findByContactAndContactType(it.getContact(), it.getContactType()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        if (existed.isEmpty()) {
            account.setUserId(user.getId());
            account.getContacts().forEach(it->it.setAccount(account));
            return accountRepository.save(account);
        }
        throw new RuntimeException();
    }

    @Override
    public Page<Account> getAllAccounts(Specification<Account> specification, Pageable pageable) {
        return accountRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional
    public Contact createContact(Long currentUserId, Contact contact) {
        contactRepository
                .findByContactAndContactType(contact.getContact(), contact.getContactType())
                .ifPresent((it)->{throw new RuntimeException();});
        Account currentAccount = accountRepository.findByUserId(currentUserId).orElseThrow();
        contact.setAccount(currentAccount);
        return contactRepository.save(contact);
    }

    @Override
    @Transactional
    public void deleteContact(Long currentUserId, Long contactId) {
        Account account = accountRepository.findByUserId(currentUserId).orElseThrow();
        if(checkDeletePermission(account, contactId)) contactRepository.deleteById(contactId);
        else throw new RuntimeException();
    }



    @Override
    @Transactional
    public Contact replaceContact(Long currentUserId, Long contactId, Contact contact) {
        Account currentAccount = accountRepository.findByUserId(currentUserId).orElseThrow();
        Contact original = contactRepository.findById(contactId).orElseThrow();
        if (!original.getAccount().equals(currentAccount)) throw new RuntimeException();
        contactRepository
                .findByContactAndContactType(contact.getContact(), contact.getContactType())
                .ifPresent((it)->{throw new RuntimeException();});
        replaceContactFields(original, contact);
        return contactRepository.save(original);
    }

    @Override
    public Optional<Account> getByUserId(Long currentUserId) {
        return accountRepository.findByUserId(currentUserId);
    }

    private void replaceContactFields(Contact original, Contact newContract) {
        original.setContact(newContract.getContact());
        original.setContactType(newContract.getContactType());
    }
    private boolean checkDeletePermission(Account account, Long contactId) {
        return account.getContacts().size()>minContactsAmount&&account.getContacts().stream().anyMatch(it->it.getId().equals(contactId));
    }





}
