package com.ecommerce.project.JwtAuth;

import java.util.Arrays;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ecommerce.project.DAO.RoleDAO;
import com.ecommerce.project.DAO.UserDAO;
import com.ecommerce.project.model.AppRole;
import com.ecommerce.project.model.Role;
import com.ecommerce.project.model.User;
import com.ecommerce.project.JwtAuth.jwt.AuthEntryPointJwt;
import com.ecommerce.project.JwtAuth.jwt.AuthTokenFilter;
import com.ecommerce.project.JwtAuth.jwt.JwtUtils;
import com.ecommerce.project.JwtAuth.services.UserDetailsServiceImpl;

/**
 * Main security configuration class for JWT-based authentication.
 * Sets up Spring Security with JWT tokens instead of sessions.
 * Configures which endpoints require authentication and which are public.
 * Also initializes default users (admin and regular user) on startup.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final JwtUtils jwtUtils;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt unauthorizedHandler, JwtUtils jwtUtils) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Configures CORS to allow frontend apps to make requests.
     * Allows localhost (for development) and production Netlify URL.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:5173",
            "http://localhost:3000",
            "https://mellow-unicorn-811456.netlify.app"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Creates the JWT authentication filter.
     * This filter checks every request for a valid JWT token.
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter(jwtUtils, userDetailsService);
    }

    /**
     * Sets up authentication provider with user details service and password encoder.
     * Used to verify username and password during login.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Provides the authentication manager for login processing.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Creates a password encoder using BCrypt hashing algorithm.
     * BCrypt is secure and automatically handles salting.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Main security filter chain configuration.
     * Combines all security settings (authentication, filters, etc.).
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        configureSecurityDefaults(http);
        configureAuthenticationProvider(http);
        configureFiltersAndHeaders(http);
        return http.build();
    }

    /**
     * Ignores security for API documentation endpoints.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/v2/api-docs",
                "/configuration/security",
                "/webjars/**");
    }

    /**
     * Initializes default data on application startup.
     * Creates default roles and users (admin and regular user) if they don't exist.
     */
    @Bean
    public CommandLineRunner initData(RoleDAO roleDAO, UserDAO userDAO, PasswordEncoder passwordEncoder) {
        return args -> {
            Role userRole = fetchOrCreateRole(roleDAO, AppRole.ROLE_USER);
            Role adminRole = fetchOrCreateRole(roleDAO, AppRole.ROLE_ADMIN);

            Set<Role> userRoles = Set.of(userRole);
            Set<Role> adminRoles = Set.of(userRole, adminRole);

            createOrUpdateUser(userDAO, passwordEncoder, "user1", "user1@example.com", "user1Pass", userRoles);
            createOrUpdateUser(userDAO, passwordEncoder, "admin", "admin@example.com", "adminPass", adminRoles);
        };
    }

    private void configureSecurityDefaults(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> configureAuthorizationRules(auth));
    }

    private void configureAuthorizationRules(org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
                .requestMatchers("/api/admin/**", "/api/auth/admin/**").hasRole("ADMIN")

                // Public authentication endpoints
                .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()

                // All other /api/**
                .requestMatchers("/api/**").permitAll()


                // Static resources
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/images/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // All other endpoints require authentication
                .anyRequest().authenticated();
    }

    private void configureAuthenticationProvider(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());
    }

    private void configureFiltersAndHeaders(HttpSecurity http) throws Exception {
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));
    }

    private Role fetchOrCreateRole(RoleDAO roleDAO, AppRole roleName) {
        return roleDAO.findByRoleName(roleName)
                .orElseGet(() -> {
                    Role newRole = new Role(roleName);
                    return roleDAO.save(newRole);
                });
    }

    private void createOrUpdateUser(UserDAO userDAO, PasswordEncoder passwordEncoder, String userName,
                                    String email, String password, Set<Role> roles) {
        if (!userDAO.existsByUserName(userName)) {
            User newUser = new User(userName, email, passwordEncoder.encode(password));
            newUser.setRoles(roles);
            userDAO.save(newUser);
        } else {
            userDAO.findByUserName(userName).ifPresent(existingUser -> {
                existingUser.setRoles(roles);
                userDAO.save(existingUser);
            });
        }
    }
}
