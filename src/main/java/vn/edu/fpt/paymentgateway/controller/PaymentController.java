package vn.edu.fpt.paymentgateway.controller;

import com.stripe.Stripe;
import com.stripe.model.*;
import com.stripe.net.ApiResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.paymentgateway.constants.PaymentSupplierEnum;
import vn.edu.fpt.paymentgateway.payload.request.StripePaymentCreateRequest;
import vn.edu.fpt.paymentgateway.payload.request.VNPayPaymentCreateRequest;
import vn.edu.fpt.paymentgateway.payload.response.BaseResponse;
import vn.edu.fpt.paymentgateway.services.PaymentFactory;
import vn.edu.fpt.paymentgateway.services.PaymentService;

import com.stripe.net.Webhook;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {

    private PaymentService paymentService;

    @Value("${stripe.apiKey}")
    private String stripeApiKey;

    @Value("${stripe.scretKet}")
    private String stripeSecret;


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

    @PutMapping("/stripe/refund")
    public ResponseEntity<?> refundPayment(HttpServletRequest httpServletRequest,
                                           @RequestParam(name = "orderId") String orderId) {
        paymentService = PaymentFactory.getPayment(httpServletRequest, PaymentSupplierEnum.STRIPE);
        paymentService.refund(orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/stripe/webhook")
    public ResponseEntity<?> webhook(HttpServletRequest servletRequest) {
        paymentService = PaymentFactory.getPayment(servletRequest, PaymentSupplierEnum.STRIPE);
        Stripe.apiKey = stripeApiKey;
        Event event = null;
        try {
            String payload = servletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

            if (!StringUtils.isEmpty(payload)) {
                String sigHeader = servletRequest.getHeader("Stripe-Signature");
                String endpointSecret = stripeSecret;
                event = ApiResource.GSON.fromJson(payload, Event.class);

                event = Webhook.constructEvent(
                        payload, sigHeader, endpointSecret
                );

                EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
                StripeObject stripeObject = null;
                if (dataObjectDeserializer.getObject().isPresent()) {
                    stripeObject = dataObjectDeserializer.getObject().get();
                } else {
                    throw new IllegalArgumentException(String.format("Unable to deserialize event data object for event %s", event.getId()
                    ));
                }

                if (event.getType().equalsIgnoreCase("charge.succeeded")) {
                    Charge charge = (Charge) stripeObject;
                    Map<String, String> paymentMetadata = charge.getMetadata();
                    String orderId = paymentMetadata.get("order_id");
                    Long amount = charge.getAmount();
                    String transactionId = charge.getId();
                    paymentService.saveInfoToRefund(orderId, transactionId, amount, "PENDING");
                }
            }
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
