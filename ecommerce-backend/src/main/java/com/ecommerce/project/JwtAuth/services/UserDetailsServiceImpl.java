package com.ecommerce.project.JwtAuth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.project.model.User;
import com.ecommerce.project.DAO.UserDAO;

/**
 * Service for loading user-specific data for Spring Security.
 * Used during authentication to fetch user details from database.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserDAO userDAO;

    /**
     * Loads a user by username for authentication.
     * Called by Spring Security during login process.
     * Throws exception if user doesn't exist.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }


}