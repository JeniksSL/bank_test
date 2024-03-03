package com.bankoperations.test.specification;

import com.bankoperations.test.domain.Account;
import com.bankoperations.test.dto.AccSearchRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface AccountSpecificationBuilder {

    Specification<Account> buildSpecification(AccSearchRequestDto criteria);

    Pageable buildPageable(AccSearchRequestDto searchRequestDto);
}
