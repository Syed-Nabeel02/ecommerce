package com.ecommerce.project.DAO;

import com.ecommerce.project.model.PaymentCard;
import com.ecommerce.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * DAO (Data Access Object) for PaymentCard entity.
 * Handles all database operations for saved payment cards.
 * Extends JpaRepository to get built-in CRUD methods (save, find, delete, etc.).
 */
public interface PaymentCardDAO extends JpaRepository<PaymentCard, Long> {

    /**
     * Finds a user's default payment card.
     * Returns Optional because a user might not have a default card set.
     */
    Optional<PaymentCard> findByUserAndIsDefaultTrue(User user);
}
