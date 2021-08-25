package com.byteworks.byteworksfoodapp.Controllers;

import com.byteworks.byteworksfoodapp.Exceptions.BadRequestException;
import com.byteworks.byteworksfoodapp.Models.Enums.OrderStatus;
import com.byteworks.byteworksfoodapp.Payload.Request.OrderRequest;
import com.byteworks.byteworksfoodapp.Payload.Response.OrderResponse;
import com.byteworks.byteworksfoodapp.Payload.Response.Response;
import com.byteworks.byteworksfoodapp.Repositories.UserRepository;
import com.byteworks.byteworksfoodapp.Services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> doCheckOut(@RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.doCheckOut(orderRequest), HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllOrdersByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(orderService.getAllOrdersByUserId(userId), HttpStatus.OK);
    }


    @GetMapping("/my-orders")
    public ResponseEntity<?> getAllLoggedUserOrders() {
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            Long userId = userRepository.findByEmail(SecurityContextHolder.getContext()
                    .getAuthentication().getName()).get().getId();
            return new ResponseEntity<>(orderService.getAllOrdersByUserId(userId), HttpStatus.OK);
        } else throw new BadRequestException("You need to be logged in");
    }


    @GetMapping("/orders-by-ref")
    public ResponseEntity<?> getOrderByReferenceNumber(@Param("orderReferenceNumber") String orderReferenceNumber) {
            return new ResponseEntity<>(orderService.getOrderByReferenceNumber(orderReferenceNumber), HttpStatus.OK);
    }


    @PostMapping("/update-order-status/{orderId}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, OrderStatus newStatus) {
        return new ResponseEntity<>(
                new Response("Order Status successfully updated", 200), HttpStatus.OK);
    }
}