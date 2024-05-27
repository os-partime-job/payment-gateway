package vn.edu.fpt.paymentgateway.services;


import vn.edu.fpt.paymentgateway.payload.PaymentCreateRequest;
import vn.edu.fpt.paymentgateway.payload.PaymentCreateResponse;

import javax.servlet.http.HttpServletRequest;

public interface PaymentService {
    PaymentCreateResponse createPayment(HttpServletRequest httpServletRequest, PaymentCreateRequest request);
}
