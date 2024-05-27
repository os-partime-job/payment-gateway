package vn.edu.fpt.paymentgateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.paymentgateway.payload.PaymentCreateRequest;
import vn.edu.fpt.paymentgateway.payload.PaymentCreateResponse;
import vn.edu.fpt.paymentgateway.third_party.vnpay.service.VNPAYService;

import javax.servlet.http.HttpServletRequest;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private VNPAYService vnpayService;

    @Override
    public PaymentCreateResponse createPayment(HttpServletRequest httpServletRequest,
                                               PaymentCreateRequest request) {

        String paymentUrl = vnpayService.createPaymentUrl(httpServletRequest, request.getOrderInfo(), request.getAmount());

        return new PaymentCreateResponse(paymentUrl);
    }
}
