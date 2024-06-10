package vn.edu.fpt.paymentgateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.paymentgateway.constants.PaymentSupplierEnum;
import vn.edu.fpt.paymentgateway.payload.request.StripePaymentCreateRequest;
import vn.edu.fpt.paymentgateway.payload.request.VNPayPaymentCreateRequest;
import vn.edu.fpt.paymentgateway.payload.response.BaseResponse;
import vn.edu.fpt.paymentgateway.payload.PaymentCreateRequest;
import vn.edu.fpt.paymentgateway.services.PaymentFactory;
import vn.edu.fpt.paymentgateway.services.PaymentService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {

    private PaymentService paymentService;

    @PostMapping("/vnp/create")
    public ResponseEntity<?> createPaymentVnpay(HttpServletRequest servletRequest,
                                                @RequestBody VNPayPaymentCreateRequest request) {
        paymentService = PaymentFactory.getPayment(servletRequest, PaymentSupplierEnum.VNPAY);
        return ResponseEntity.ok(BaseResponse.ok(paymentService.createPayment(servletRequest, request)));
    }

    @PostMapping("/stripe/create")
    public ResponseEntity<?> createPaymentStripe(HttpServletRequest servletRequest,
                                                 @RequestBody StripePaymentCreateRequest request) {
        paymentService = PaymentFactory.getPayment(servletRequest, PaymentSupplierEnum.STRIPE);
        return ResponseEntity.ok(BaseResponse.ok(paymentService.createPayment(servletRequest, request)));
    }

}
