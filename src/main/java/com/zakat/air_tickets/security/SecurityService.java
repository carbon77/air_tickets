package com.zakat.air_tickets.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.zakat.air_tickets.entity.User;
import com.zakat.air_tickets.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {

    private final String LOGIN_SUCCESS_URL = "/";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetails getAuthenticatedUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Object principal = securityContext.getAuthentication().getPrincipal();

        if (principal instanceof UserPrincipal) {
            return (UserDetails) principal;
        }

        return null;
    }

    public User createUser(String username, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setPassword(encodedPassword);
        user.setEmail(email);
        user.setUsername(username);
        user.setRole("USER");

        return userRepository.save(user);
    }

    public void logout() {
        UI.getCurrent().getPage().setLocation(LOGIN_SUCCESS_URL);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(
                VaadinServletRequest.getCurrent().getHttpServletRequest(), null,
                null
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserPrincipal(user);
    }
}
