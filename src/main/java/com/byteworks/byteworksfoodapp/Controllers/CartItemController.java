package com.byteworks.byteworksfoodapp.Controllers;

import com.byteworks.byteworksfoodapp.Payload.Request.CartItemRequest;
import com.byteworks.byteworksfoodapp.Services.CartItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartItemController {

    private final CartItemService cartItemService;
    private final ModelMapper modelMapper;

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addItemsToCart(@RequestBody CartItemRequest cartItemRequest){
        return new
                ResponseEntity<>(cartItemService.persistCartItem(cartItemRequest), HttpStatus.CREATED);
    }


    @GetMapping
    @CrossOrigin
    public ResponseEntity<?> getCartItems() {
        return new ResponseEntity<>(
                cartItemService.getCartItems()
                        .stream().map(item->modelMapper.map(item, CartItemRequest.class))
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    @CrossOrigin
    public ResponseEntity<?> deleteCartItem(@PathVariable Long id) {
        return new ResponseEntity<>(cartItemService.deleteCartItemById(id), HttpStatus.ACCEPTED);
    }
}