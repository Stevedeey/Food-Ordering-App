package com.byteworks.byteworksfoodapp.Repositories;

import com.byteworks.byteworksfoodapp.Models.CartItem;
import com.byteworks.byteworksfoodapp.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    boolean existsByUserAndFoodId(User user, Long foodId);

    Optional<CartItem> findByUserAndFoodId(User user, Long foodId);

    Optional<List<CartItem>> findAllByUser(User user);

    Optional<List<CartItem>> findAllByUserAndStatus(User user, String status);

}
