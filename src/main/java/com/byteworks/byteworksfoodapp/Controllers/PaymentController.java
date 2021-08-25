package com.byteworks.byteworksfoodapp.Controllers;

import com.byteworks.byteworksfoodapp.Services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping(value = "/pay/{id}")
    public String makePayment(HttpServletResponse httpServletResponse, @PathVariable String id) throws Exception {
        Long orderId = Long.valueOf(id);
        return paymentService.getPaymentAuthorizationUrl(orderId);
    }

    @GetMapping(value = "/confirm/{order}")
    public void confirmPayment(@PathVariable String order, HttpServletResponse response) throws Exception {
        Long orderId = Long.valueOf(order);
        paymentService.confirmPayment(orderId);
        response.sendRedirect("https://www.byteworks.com.ng/");
    }
}