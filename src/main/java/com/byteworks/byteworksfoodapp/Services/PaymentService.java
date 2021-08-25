package com.byteworks.byteworksfoodapp.Services;

import com.byteworks.byteworksfoodapp.Payload.Response.Response;
import org.springframework.http.ResponseEntity;


public interface PaymentService {
    String getPaymentAuthorizationUrl(Long orderId) throws Exception;
    ResponseEntity<Response> confirmPayment(Long orderId) throws Exception;
}
