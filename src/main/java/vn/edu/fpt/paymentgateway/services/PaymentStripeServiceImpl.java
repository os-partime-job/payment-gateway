package vn.edu.fpt.paymentgateway.services;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.edu.fpt.paymentgateway.constants.PaymentSupplierEnum;
import vn.edu.fpt.paymentgateway.entity.PaymentDetail;
import vn.edu.fpt.paymentgateway.payload.request.BaseCreatePaymentRequest;
import vn.edu.fpt.paymentgateway.payload.request.StripePaymentCreateRequest;
import vn.edu.fpt.paymentgateway.payload.response.PaymentCreateResponse;
import vn.edu.fpt.paymentgateway.repo.PaymentDetailRepository;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;


@Service("paymentStripeService")
public class PaymentStripeServiceImpl implements PaymentService {

    @Autowired
    private PaymentDetailRepository paymentDetailRepository;

    @Value("${payment.callbackUrl}")
    private String callBackurl;

    @Value("${stripe.scretKet}")
    private String apiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = this.apiKey;
    }

    @Override
    public PaymentCreateResponse createPayment(HttpServletRequest httpServletRequest, BaseCreatePaymentRequest request) {
        StripePaymentCreateRequest stripePaymentCreateRequest = (StripePaymentCreateRequest) request;
        initTransaction(stripePaymentCreateRequest.getOrderId(), stripePaymentCreateRequest.getRequestId(), stripePaymentCreateRequest.getOrderInfo(),
                stripePaymentCreateRequest.getAmount(), PaymentSupplierEnum.STRIPE);
        final String onCallbackSuccess = callBackurl + "?orderId=" + stripePaymentCreateRequest.getOrderId() + "&stt=success";
        final String onCallbackFailed = callBackurl + "?orderId=" + stripePaymentCreateRequest.getOrderId() + "&stt=failed";

        try {
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setPriceData(
                                                    SessionCreateParams.LineItem.PriceData.builder()
                                                            .setUnitAmount(2000L)
                                                            .setProductData(
                                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                            .setName("T-shirt")
                                                                            .build()
                                                            )
                                                            .setCurrency("vnd")
                                                            .setUnitAmount(100000L)
                                                            .build()
                                            )
                                            .setQuantity(1L)
                                            .build()
                            )
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl(onCallbackSuccess)
                            .setCancelUrl(onCallbackFailed)
                            .build();
            Session session = Session.create(params);
            return new PaymentCreateResponse(session.getUrl());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void updatePayment(String orderId, String transactionId, String status) {

    }

    public void initTransaction(String orderId, String requestId, String orderInfo, long amount, PaymentSupplierEnum paymentSupplierEnum) {
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setOrderId(orderId);
        paymentDetail.setRequestId(requestId);
        paymentDetail.setAmount(amount);
        paymentDetail.setOrderInfo(orderInfo);
        paymentDetail.setCreatedTime(OffsetDateTime.now());
        paymentDetail.setPaymentSupplier(paymentSupplierEnum);

        paymentDetailRepository.save(paymentDetail);
    }
}
