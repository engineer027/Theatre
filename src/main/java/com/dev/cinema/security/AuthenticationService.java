package com.dev.cinema.security;

import com.dev.cinema.model.User;

public interface AuthenticationService {
    User register(User user);
}
