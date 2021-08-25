package com.byteworks.byteworksfoodapp.Models;

import com.byteworks.byteworksfoodapp.Models.Enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseModel{

    private String orderReferenceNumber;

    @ManyToOne
    private User user;

    @ManyToOne
    private Address shippingAddress;

    @OneToMany
    private List<CartItem> cartItems;

    private String couponCode;

    private Long subTotalCost;

    private Long shippingCost;

    private Long discount;

    private Long totalCost;

    private OrderStatus status;

    private Date deliveryDate;
}