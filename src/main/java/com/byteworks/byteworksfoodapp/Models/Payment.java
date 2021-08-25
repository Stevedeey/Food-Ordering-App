package com.byteworks.byteworksfoodapp.Models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@ToString
@Getter
@Entity
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends BaseModel {

    @Column(name = "payment_ref")
    private String paymentReference;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "confirm_payment")
    private boolean confirmPayment;
}