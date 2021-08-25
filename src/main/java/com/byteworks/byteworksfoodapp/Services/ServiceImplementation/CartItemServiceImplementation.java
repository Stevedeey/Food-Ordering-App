package com.byteworks.byteworksfoodapp.Services.ServiceImplementation;

import com.byteworks.byteworksfoodapp.Exceptions.ResourceNotFoundException;
import com.byteworks.byteworksfoodapp.Models.CartItem;
import com.byteworks.byteworksfoodapp.Models.User;
import com.byteworks.byteworksfoodapp.Payload.Request.CartItemRequest;
import com.byteworks.byteworksfoodapp.Repositories.CartItemRepository;
import com.byteworks.byteworksfoodapp.Repositories.FoodRepository;
import com.byteworks.byteworksfoodapp.Repositories.UserRepository;
import com.byteworks.byteworksfoodapp.Services.CartItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Transactional
@Service
public class CartItemServiceImplementation implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public String persistCartItem(CartItemRequest cartItemRequest) {

        CartItem cartItem = modelMapper.map(cartItemRequest, CartItem.class);
        cartItem.setStatus("OPEN");
        saveCartItem(cartItem);
        return "Item successfully added to cart";
    }

    @Override
    public CartItem saveCartItem(CartItem item) {
        return cartItemRepository.save(item);
    }

    @Override
    public List<CartItem> getCartItems() {
        User user =  userRepository.findByEmail(SecurityContextHolder
                .getContext().getAuthentication().getName()).get();
        return cartItemRepository.findAllByUserAndStatus(user, "OPEN").orElseThrow(() -> {
            throw new ResourceNotFoundException("No item in your cart");
        });
    }

    @Override
    public String deleteCartItemById(Long id) {
        try {
            cartItemRepository.deleteById(id);
        } catch (Exception exception) {
            throw new ResourceNotFoundException("Cart Item not found!");
        }
        return "Cart item successfully deleted";
    }
}