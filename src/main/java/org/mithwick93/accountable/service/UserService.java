package org.mithwick93.accountable.service;

import org.mithwick93.accountable.model.User;

public interface UserService {
    String createUser(User user);

    String authenticateUser(String username, String password);
}
