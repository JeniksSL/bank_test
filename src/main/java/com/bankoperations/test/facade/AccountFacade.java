package com.bankoperations.test.facade;

import com.bankoperations.test.dto.*;
import com.bankoperations.test.dto.core.PageDto;

public interface AccountFacade {
    PageDto<AccountDto> getAllAccounts(AccSearchRequestDto searchRequestDto);

    ContactDto createContact(ContactDto contactDto);

    void deleteContact(Long contactId);

    ContactDto replaceContact(Long  contactId, ContactDto contactDto);

}
