package vn.edu.fpt.paymentgateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.paymentgateway.constants.PaymentSupplierEnum;
import vn.edu.fpt.paymentgateway.services.PaymentFactory;
import vn.edu.fpt.paymentgateway.services.PaymentService;
import vn.edu.fpt.paymentgateway.third_party.vnpay.service.VNPAYUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CallbackController {

    private PaymentService paymentService;

    @Value("${payment.redirectUrl}")
    private String redirectUrl;

    @GetMapping("/callback")
    public void paymentCallback(@RequestParam Map<String, String> params, HttpServletResponse response, HttpServletRequest request) throws IOException {
        paymentService = PaymentFactory.getPayment(request, PaymentSupplierEnum.VNPAY);
        System.out.println("Get callback: " + params);
        String orderId = params.get("orderId");
        String transId = params.get("vnp_TransactionNo");
        String status = params.get("vnp_TransactionStatus");
        paymentService.updatePayment(orderId, transId, status);

        response.sendRedirect(redirectUrl + "?" + VNPAYUtils.buildQuery(params));
    }

    @PostMapping("/callback")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void retrieve(HttpServletRequest httpServletRequest) {
        System.out.println(httpServletRequest.toString());
    }
}
