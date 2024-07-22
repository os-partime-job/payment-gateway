package vn.edu.fpt.paymentgateway.services;


import vn.edu.fpt.paymentgateway.payload.PaymentCreateRequest;
import vn.edu.fpt.paymentgateway.payload.request.BaseCreatePaymentRequest;
import vn.edu.fpt.paymentgateway.payload.response.PaymentCreateResponse;

import javax.servlet.http.HttpServletRequest;

public interface PaymentService {
    PaymentCreateResponse createPayment(HttpServletRequest httpServletRequest, BaseCreatePaymentRequest request);

    void updatePayment(String orderId, String transactionId, String status);

    void saveInfoToRefund(String orderId, String chargeIdent, Long amount, String status);

    void refund(String orderId);
}
