package vn.edu.fpt.paymentgateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.paymentgateway.payload.BaseResponse;
import vn.edu.fpt.paymentgateway.payload.PaymentCheckTransactionResponse;
import vn.edu.fpt.paymentgateway.payload.PaymentCreateRequest;
import vn.edu.fpt.paymentgateway.payload.PaymentCreateResponse;
import vn.edu.fpt.paymentgateway.services.PaymentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(HttpServletRequest servletRequest,
                                           @RequestBody PaymentCreateRequest request) {
        return ResponseEntity.ok(BaseResponse.ok(paymentService.createPayment(servletRequest, request)));
    }

//    @GetMapping("/check")
//    public ResponseEntity<?> checkPayment(@RequestParam int tid) {
//        return ResponseEntity.ok(BaseResponse.ok(paymentService.checkPayment(tid)));
//    }
}
