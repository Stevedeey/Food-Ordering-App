package com.byteworks.byteworksfoodapp.Payload.Request;

import com.byteworks.byteworksfoodapp.Models.Enums.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "city cannot be empty")
    private String city;

    private List<CartItemRequest> cartItems;

    private Long subTotalCost;

    private Long shippingCost;

    private Long discount;

    private String couponCode;

    private Long totalCost;

}