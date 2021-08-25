package com.byteworks.byteworksfoodapp.Repositories;

import com.byteworks.byteworksfoodapp.Models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Optional<Payment> findPaymentByOrderId(Long orderId);

    List<Payment> findAll();
}
