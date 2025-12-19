package com.ecommerce.project.JwtAuth.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.ecommerce.project.JwtAuth.services.UserDetailsImpl;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

/**
 * Utility class for JWT (JSON Web Token) operations.
 * Handles token generation, validation, and extraction from requests.
 * JWT tokens are used for stateless authentication (no sessions needed).
 */
@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int BEARER_PREFIX_LENGTH = 7;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final int JWT_COOKIE_MAX_AGE = 24 * 60 * 60;
    private static final String API_PATH = "/api";

    @Value("${spring.app.jwtSecret}")
    private final String jwtSecret;

    @Value("${spring.app.jwtExpirationMs}")
    private final int jwtExpirationMs;

    @Value("${spring.ecom.app.jwtCookieName}")
    private final String jwtCookieName;

    private final MessageSource messageSource;

    public JwtUtils(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.jwtSecret = null;
        this.jwtExpirationMs = 0;
        this.jwtCookieName = null;
    }

    /**
     * Extracts JWT token from cookies.
     * Cookies are one way to send JWT tokens from frontend to backend.
     */
    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie jwtCookie = WebUtils.getCookie(request, jwtCookieName);
        return extractCookieValue(jwtCookie);
    }

    /**
     * Extracts JWT token from Authorization header.
     * Most common way to send JWT: "Authorization: Bearer <token>"
     */
    public String getJwtFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        return extractJwtFromBearerToken(authorizationHeader);
    }

    /**
     * Generates a JWT cookie for a logged-in user.
     * Used after successful login to send token back to frontend.
     */
    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwtToken = generateTokenFromUsername(userPrincipal.getUsername());
        return buildJwtCookie(jwtToken);
    }

    /**
     * Creates an empty cookie to clear the JWT (for logout).
     */
    public ResponseCookie getCleanJwtCookie() {
        return buildCleanCookie();
    }

    /**
     * Generates a new JWT token for a username.
     * Token contains: username, issue time, expiration time.
     * Signed with secret key to prevent tampering.
     */
    public String generateTokenFromUsername(String username) {
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extracts the username from a JWT token.
     * Used to identify who is making the request.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Validates a JWT token (checks signature, expiration, format).
     * Returns true if token is valid, false otherwise.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) getSigningKey())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException exception) {
            logTokenValidationError("jwt.error.invalid", exception);
        } catch (ExpiredJwtException exception) {
            logTokenValidationError("jwt.error.expired", exception);
        } catch (UnsupportedJwtException exception) {
            logTokenValidationError("jwt.error.unsupported", exception);
        } catch (IllegalArgumentException exception) {
            logTokenValidationError("jwt.error.empty", exception);
        }
        return false;
    }

    private String extractCookieValue(Cookie jwtCookie) {
        return jwtCookie != null ? jwtCookie.getValue() : null;
    }

    private String extractJwtFromBearerToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            return authorizationHeader.substring(BEARER_PREFIX_LENGTH);
        }
        return null;
    }

    private ResponseCookie buildJwtCookie(String jwtToken) {
        return ResponseCookie.from(jwtCookieName, jwtToken)
                .path(API_PATH)
                .maxAge(JWT_COOKIE_MAX_AGE)
                .httpOnly(false)
                .secure(false)
                .build();
    }

    private ResponseCookie buildCleanCookie() {
        return ResponseCookie.from(jwtCookieName, null)
                .path(API_PATH)
                .build();
    }

    private Key getSigningKey() {
        byte[] decodedKey = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    private void logTokenValidationError(String messageKey, Exception exception) {
        String message = messageSource.getMessage(
                messageKey,
                new Object[]{exception.getMessage()},
                exception.getMessage(),
                LocaleContextHolder.getLocale()
        );
        logger.error(message);
    }
}
