package com.byteworks.byteworksfoodapp.Repositories;

import com.byteworks.byteworksfoodapp.Models.Order;
import com.byteworks.byteworksfoodapp.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderReferenceNumber(String orderReferenceNumber);

    Optional<List<Order>> findAllByUser(User loggedUser);
}
