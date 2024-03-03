package com.bankoperations.test.service;

import com.bankoperations.test.domain.User;

public interface UserService {
    User create(User user);
    Long getCurrentUserId();

}
