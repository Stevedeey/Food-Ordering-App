package com.byteworks.byteworksfoodapp.Services;

import com.byteworks.byteworksfoodapp.Models.CartItem;
import com.byteworks.byteworksfoodapp.Payload.Request.CartItemRequest;

import java.util.List;

public interface CartItemService {

    String persistCartItem(CartItemRequest cartItemRequest);

    CartItem saveCartItem(CartItem item);

    List<CartItem> getCartItems();

    String deleteCartItemById(Long id);
}