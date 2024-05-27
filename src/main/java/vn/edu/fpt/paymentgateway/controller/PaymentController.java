package vn.edu.fpt.paymentgateway.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.paymentgateway.payload.PaymentCreateRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {

    @PostMapping("/create")
    public String createPayment(HttpServletRequest servletRequest,
                                PaymentCreateRequest request) {

        return "Payment created";
    }
}
