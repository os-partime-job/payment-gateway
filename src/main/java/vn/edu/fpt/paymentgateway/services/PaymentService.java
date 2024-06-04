package vn.edu.fpt.paymentgateway.services;


import vn.edu.fpt.paymentgateway.payload.PaymentCheckTransactionResponse;
import vn.edu.fpt.paymentgateway.payload.PaymentCreateRequest;
import vn.edu.fpt.paymentgateway.payload.PaymentCreateResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface PaymentService {
    PaymentCreateResponse createPayment(HttpServletRequest httpServletRequest, PaymentCreateRequest request);

    PaymentCheckTransactionResponse checkPayment(int tid);

    void updatePayment(String orderId, long transactionId, String status);
}
