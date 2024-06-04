package vn.edu.fpt.paymentgateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.paymentgateway.services.PaymentService;
import vn.edu.fpt.paymentgateway.third_party.vnpay.service.VNPAYUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CallbackController {

    @Autowired
    private PaymentService paymentService;

    @Value("${payment.redirectUrl}")
    private String redirectUrl;

    @GetMapping("/callback")
    public void paymentCallback(@RequestParam Map<String, String> params, HttpServletResponse response) throws IOException {
        System.out.println("Get callback: " + params);
        String orderId = params.get("orderId");
        long transId = Long.parseLong(params.get("vnp_TransactionNo"));
        String status = params.get("vnp_TransactionStatus");

        paymentService.updatePayment(orderId, transId, status);

        response.sendRedirect(redirectUrl + "?" + VNPAYUtils.buildQuery(params));
    }
}
