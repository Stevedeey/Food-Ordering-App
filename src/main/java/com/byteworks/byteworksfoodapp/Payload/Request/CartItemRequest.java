package com.byteworks.byteworksfoodapp.Payload.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequest {

    private Long foodId;

    private Long quantity;

    private Long price;

    private Date deliveryDate = new Date();

    private String otherInfo;

}
