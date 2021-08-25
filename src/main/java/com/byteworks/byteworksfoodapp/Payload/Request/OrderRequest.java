package com.byteworks.byteworksfoodapp.Payload.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest implements Serializable {

    private String name;

    private String email;

    private String phoneNumber;

    private String shippingAddress;

    private List<CartItemRequest> cartItems;

    private Long subTotalCost;

    private Long shippingCost;

    private Long discount;

    private String couponCode;

    private Long totalCost;

}