package com.byteworks.byteworksfoodapp.Services.ServiceImplementation;

import com.byteworks.byteworksfoodapp.Configuration.paystackconfiguration.InitializeTransaction;
import com.byteworks.byteworksfoodapp.Configuration.paystackconfiguration.InitializeTransactionRequest;
import com.byteworks.byteworksfoodapp.Configuration.paystackconfiguration.InitializeTransactionResponse;
import com.byteworks.byteworksfoodapp.Configuration.paystackconfiguration.PayStackVerifyTransaction;
import com.byteworks.byteworksfoodapp.Exceptions.ResourceNotFoundException;
import com.byteworks.byteworksfoodapp.Models.Order;
import com.byteworks.byteworksfoodapp.Models.Payment;
import com.byteworks.byteworksfoodapp.Payload.Response.Response;
import com.byteworks.byteworksfoodapp.Repositories.OrderRepository;
import com.byteworks.byteworksfoodapp.Repositories.PaymentRepository;
import com.byteworks.byteworksfoodapp.Services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class PaymentServiceImplementation implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public String getPaymentAuthorizationUrl(Long orderId) throws Exception {

        Order order = orderRepository.getById(orderId);

        InitializeTransactionRequest request = new InitializeTransactionRequest();
        request.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        request.setAmount((int) (order.getTotalCost() * 100));

       request.setCallback_url("http://localhost:8080/api/payment/confirm/" + orderId);

        InitializeTransactionResponse response = InitializeTransaction.initTransaction(request);
        String authorizationUrl = response.getData().getAuthorization_url();

        if(!response.getStatus()){
            return null;
        }

        try {
            Payment payment = new Payment();
            payment.setOrderId(orderId);
            payment.setConfirmPayment(false);
            payment.setPaymentReference(response.getData().getReference());
            paymentRepository.save(payment);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return authorizationUrl;
    }


    public ResponseEntity<Response> confirmPayment(Long orderId) throws Exception {

        Response response = new Response();

        Order order = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order with orderid not found"));

        Optional<Payment> paymentOptional = paymentRepository.findPaymentByOrderId(orderId);

        if(paymentOptional.isEmpty()) {
            return new ResponseEntity<>(
                    new Response("Order Not Found", 404),
                    HttpStatus.NOT_FOUND);
        }

        //payment reference
        String paymentReference = paymentOptional.get().getPaymentReference();

        PayStackVerifyTransaction payStackVerifyTransaction = new PayStackVerifyTransaction();
        payStackVerifyTransaction = payStackVerifyTransaction.verifyTransaction(paymentReference);

        if(payStackVerifyTransaction == null){
            paymentOptional.get().setConfirmPayment(true);
            paymentRepository.save(paymentOptional.get());

            response.setStatus_code(201);
            response.setMessage("Payment already made on this order");

        }else{

            if (!payStackVerifyTransaction.getData().getStatus().equals("success")){
                response.setStatus_code(200);
                response.setMessage("Payment has not been made yet on this order");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
