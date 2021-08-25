package com.byteworks.byteworksfoodapp.Payload.Response;

import com.byteworks.byteworksfoodapp.Models.Address;
import com.byteworks.byteworksfoodapp.Models.CartItem;
import com.byteworks.byteworksfoodapp.Models.Enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponse {

    private String orderReferenceNumber;

    private Address shippingAddress;

    private List<CartItem> cartItems;

    private Long subTotalCost;

    private Long shippingCost;

    private Long discount;

    private Long totalCost;

    private OrderStatus status;

    private Date deliveryDate;


    public OrderResponse(String orderReferenceNumber, Address shippingAddress,
                         List<CartItem> cartItems, Long totalCost, OrderStatus status) {

        this.orderReferenceNumber = orderReferenceNumber;
        this.shippingAddress = shippingAddress;
        this.cartItems = cartItems;
        this.totalCost = totalCost;
        this.status = status;

    }
}
