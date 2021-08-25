package com.byteworks.byteworksfoodapp.Services;

import com.byteworks.byteworksfoodapp.Models.Enums.OrderStatus;
import com.byteworks.byteworksfoodapp.Models.Order;
import com.byteworks.byteworksfoodapp.Payload.Request.OrderRequest;
import com.byteworks.byteworksfoodapp.Payload.Response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse doCheckOut(OrderRequest orderRequest);
    List<Order> getAllOrders();

    List<Order> getAllOrdersByUserId(Long UserId);

    Order getOrderByReferenceNumber(String orderReferenceNumber);

    void updateOrderStatus(Long orderId, OrderStatus newStatus);
}
