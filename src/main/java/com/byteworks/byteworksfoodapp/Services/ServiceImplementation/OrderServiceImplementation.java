package com.byteworks.byteworksfoodapp.Services.ServiceImplementation;

import com.byteworks.byteworksfoodapp.Exceptions.BadRequestException;
import com.byteworks.byteworksfoodapp.Exceptions.ResourceNotFoundException;
import com.byteworks.byteworksfoodapp.Models.Address;
import com.byteworks.byteworksfoodapp.Models.CartItem;
import com.byteworks.byteworksfoodapp.Models.Enums.City;
import com.byteworks.byteworksfoodapp.Models.Enums.ERole;
import com.byteworks.byteworksfoodapp.Models.Enums.OrderStatus;
import com.byteworks.byteworksfoodapp.Models.Order;
import com.byteworks.byteworksfoodapp.Models.User;
import com.byteworks.byteworksfoodapp.Payload.Request.CartItemRequest;
import com.byteworks.byteworksfoodapp.Payload.Request.OrderRequest;
import com.byteworks.byteworksfoodapp.Payload.Response.OrderResponse;
import com.byteworks.byteworksfoodapp.Repositories.AddressRepository;
import com.byteworks.byteworksfoodapp.Repositories.CartItemRepository;
import com.byteworks.byteworksfoodapp.Repositories.OrderRepository;
import com.byteworks.byteworksfoodapp.Repositories.UserRepository;
import com.byteworks.byteworksfoodapp.Services.OrderService;
import com.byteworks.byteworksfoodapp.Utils.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class OrderServiceImplementation implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Utility utility;
    private final CartItemRepository cartItemRepository;
    private final AddressRepository addressRepository;


    @Override
    public OrderResponse doCheckOut(OrderRequest orderRequest) {

        //Processing User Data
        String email = orderRequest.getEmail();
        User user = new User();
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            user = userRepository.getByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        }

        else if(userRepository.existsByEmail(email)){
            user = userRepository.getByEmail(email);
        }

        else{
            user.setEmail(email);
            user.setPassword(bCryptPasswordEncoder.encode(email));
            user.setRoles(utility.assignRole(ERole.USER));
            userRepository.save(user);
        }

        //Processing CartItems
        List<CartItem> cartItems = new ArrayList<>();
        for(CartItemRequest cartItemRequest : orderRequest.getCartItems()){
            cartItems.add(cartItemRepository.save(new CartItem(
                    cartItemRequest.getFoodId(),
                    cartItemRequest.getQuantity(),
                    cartItemRequest.getPrice(),
                    cartItemRequest.getDeliveryDate(),
                    "CLOSED",
                    cartItemRequest.getOtherInfo(),
                    user
            )));
        }

        Set<String> cities = new HashSet<>(List.of("ABUJA",
                "LAGOS", "IBADAN",
                "UYO", "PORT_HARCOURT",
                "ENUGU", "ASABA",
                "KANO", "UMUAHIA",
                "ONITSHA", "ABA", "OWERRI"));

        City requestCity;

        if(!cities.contains(orderRequest.getCity().toUpperCase()))
            throw new BadRequestException("Invalid City");
        else{
            requestCity = City.valueOf(orderRequest.getCity());
        }

        //Processing the Address
        Address address = new Address(orderRequest.getName(),
                orderRequest.getPhoneNumber(),
                orderRequest.getShippingAddress(),
                requestCity);
        addressRepository.save(address);
        List<Address> userAddresses = user.getAddresses();
        userAddresses.add(address);

        //Processing the Order
        Order order = new Order();
        orderRepository.save(order);
        order.setShippingAddress(address);
        order.setCartItems(cartItems);
        order.setSubTotalCost(orderRequest.getSubTotalCost());
        order.setShippingCost(orderRequest.getShippingCost());
        order.setStatus(OrderStatus.ORDER_PLACED);
        order.setDiscount(orderRequest.getDiscount());
        order.setTotalCost(orderRequest.getTotalCost());
        order.setOrderReferenceNumber(UUID.randomUUID().toString().toUpperCase().substring(0,3)
                + order.getId() + UUID.randomUUID().toString().toUpperCase().substring(0,4));


        return new OrderResponse(
                order.getOrderReferenceNumber(),
                order.getShippingAddress(),
                order.getCartItems(),
                order.getTotalCost(),
                order.getStatus()
        );
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    @Override
    public List<Order> getAllOrdersByUserId(Long UserId) {
        User loggedUser = userRepository.findById(UserId).orElseThrow(
                () -> new ResourceNotFoundException
                        ("User with email "+ UserId +" does not exist")
        );
        return orderRepository.findAllByUser(loggedUser).orElse(new ArrayList<>());
    }


    @Override
    public Order getOrderByReferenceNumber(String orderReferenceNumber) {
        return orderRepository.findByOrderReferenceNumber(orderReferenceNumber).orElseThrow(
                () -> new ResourceNotFoundException
                        ("Order with reference number "+ orderReferenceNumber +" does not exist")
        );
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("Order with id number "+ orderId +" does not exist")
        );
        order.setStatus(newStatus);
    }
}