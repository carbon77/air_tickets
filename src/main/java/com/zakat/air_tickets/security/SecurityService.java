package com.zakat.air_tickets.security;

import com.vaadin.flow.spring.security.AuthenticationContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final AuthenticationContext authenticationContext;

    public UserPrincipal getAuthenticatedUser() {
        return authenticationContext.getAuthenticatedUser(UserPrincipal.class).get();
    }

    public void logout() {
        authenticationContext.logout();
    }
}
