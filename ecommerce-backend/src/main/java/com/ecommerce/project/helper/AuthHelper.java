package com.ecommerce.project.helper;

import com.ecommerce.project.DAO.UserDAO;
import com.ecommerce.project.model.User;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Helper class for authentication-related operations.
 * Provides convenient methods to get info about the currently logged-in user.
 * Used throughout the app to check who is making the request.
 */
@Component
public class AuthHelper {

    private final UserDAO userDAO;
    private final MessageSource messageSource;

    public AuthHelper(UserDAO userDAO, MessageSource messageSource) {
        this.userDAO = userDAO;
        this.messageSource = messageSource;
    }

    /**
     * Fetches the currently authenticated user from the database.
     * Gets username from Spring Security context and looks up the user.
     */
    private User fetchAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : "unknown";

        String msg = messageSource.getMessage(
                "auth.user.notFound",
                new Object[]{username},
                "User Not Found with username: " + username,
                LocaleContextHolder.getLocale()
        );

        return userDAO.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(msg));
    }

    /**
     * Returns the email of the currently logged-in user.
     * Useful for operations that need to verify user identity.
     */
    public String loggedInEmail() {
        return fetchAuthenticatedUser().getEmail();
    }

    /**
     * Returns the ID of the currently logged-in user.
     * Used for database queries specific to the current user.
     */
    public Long loggedInUserId() {
        return fetchAuthenticatedUser().getUserId();
    }

    /**
     * Returns the full User object of the currently logged-in user.
     * Use when you need complete user information (roles, addresses, etc.).
     */
    public User loggedInUser() {
        return fetchAuthenticatedUser();
    }
}
