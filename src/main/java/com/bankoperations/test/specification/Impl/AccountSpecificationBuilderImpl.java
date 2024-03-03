package com.bankoperations.test.specification.Impl;

import com.bankoperations.test.domain.Account;
import com.bankoperations.test.domain.Contact;
import com.bankoperations.test.dto.AccSearchRequestDto;
import com.bankoperations.test.dto.core.ContactType;
import com.bankoperations.test.specification.AccountSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class AccountSpecificationBuilderImpl implements AccountSpecificationBuilder {

    @Override
    public Specification<Account> buildSpecification(AccSearchRequestDto criteria) {
        Specification<Account> specification = Specification.where((root, criteriaQuery, criteriaBuilder)->criteriaBuilder.conjunction());
        if (Objects.nonNull(criteria.name())) specification= specification.and(getByNameLike(criteria.name()));
        if (Objects.nonNull(criteria.birthDate())) specification= specification.and(getByGreaterBirthDate(LocalDate.parse(criteria.birthDate())));
        if (Objects.nonNull(criteria.email())) specification= specification.and(getByContact(criteria.email(), ContactType.EMAIL));
        if (Objects.nonNull(criteria.phone())) specification= specification.and(getByContact(criteria.phone(), ContactType.PHONE));
        return specification;
    }

    @Override
    public Pageable buildPageable(AccSearchRequestDto searchRequestDto) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (searchRequestDto.isDesc()!=null&&searchRequestDto.isDesc())
            direction=Sort.Direction.DESC;
        Sort sort = Sort.unsorted();
        if (Objects.nonNull(searchRequestDto.accountOrder()))
            sort = sort.and(Sort.by(direction, searchRequestDto.accountOrder().getProperty()));
        return PageRequest.of(searchRequestDto.page(), searchRequestDto.size(), sort);
    }

    private Specification<Account> getByNameLike(String name) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), name + "%");
    }

    private Specification<Account> getByGreaterBirthDate(LocalDate birthDate) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("birthDate"), birthDate);
    }

    private Specification<Account> getByContact(String contact, ContactType contactType) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.isMember(contact, root.get("contacts").get("contact")),
                criteriaBuilder.isMember(contactType, root.get("contacts").get("contactType"))
        );
    }



}
