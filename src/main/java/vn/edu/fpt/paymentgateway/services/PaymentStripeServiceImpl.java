package vn.edu.fpt.paymentgateway.services;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.edu.fpt.paymentgateway.constants.PaymentSupplierEnum;
import vn.edu.fpt.paymentgateway.entity.PaymentDetail;
import vn.edu.fpt.paymentgateway.exception.PaymentGatewayException;
import vn.edu.fpt.paymentgateway.payload.request.BaseCreatePaymentRequest;
import vn.edu.fpt.paymentgateway.payload.request.StripePaymentCreateRequest;
import vn.edu.fpt.paymentgateway.payload.response.PaymentCreateResponse;
import vn.edu.fpt.paymentgateway.repo.OrdersRepository;
import vn.edu.fpt.paymentgateway.repo.PaymentDetailRepository;
import vn.edu.fpt.paymentgateway.third_party.vnpay.contants.VNPAYConstants;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;


@Service("paymentStripeService")
public class PaymentStripeServiceImpl implements PaymentService {

    @Autowired
    private PaymentDetailRepository paymentDetailRepository;

    @Autowired
    private OrdersRepository ordersRepository;

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
        ordersRepository.findByUniqueOrderId(ordersRepository.findByUniqueOrderId(stripePaymentCreateRequest.getOrderId()).orElseThrow(() -> new PaymentGatewayException("Không tìm thấy orderId: " + stripePaymentCreateRequest.getOrderId())).getUniqueOrderId()).orElseThrow(() -> new PaymentGatewayException("Không tìm thấy orderId: " + stripePaymentCreateRequest.getOrderId()));
        callBackurl = callBackurl + "?gateway=" + PaymentSupplierEnum.STRIPE.name();
        final String onCallbackSuccess = callBackurl + "&orderId=" + stripePaymentCreateRequest.getOrderId() + "&status=0";
        final String onCallbackFailed = callBackurl + "&orderId=" + stripePaymentCreateRequest.getOrderId() + "&status=2";

        try {
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setPriceData(
                                                    SessionCreateParams.LineItem.PriceData.builder()
                                                            .setProductData(
                                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                            .setName("Jewrly")
                                                                            .build()
                                                            )
                                                            .setCurrency("vnd")
                                                            .setUnitAmount(request.getAmount())
                                                            .build()
                                            )
                                            .setQuantity(1L)
                                            .build()
                            )
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl(onCallbackSuccess + "&transId=" + stripePaymentCreateRequest.getOrderId())
                            .setCancelUrl(onCallbackFailed + "&transId=" + stripePaymentCreateRequest.getOrderId())
                            .build();
            Session session = Session.create(params);
            initTransaction(stripePaymentCreateRequest.getOrderId(), stripePaymentCreateRequest.getRequestId(), session.getId(), stripePaymentCreateRequest.getOrderInfo(),
                    stripePaymentCreateRequest.getAmount(), PaymentSupplierEnum.STRIPE);
            return new PaymentCreateResponse(session.getUrl());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void updatePayment(String orderId, String transactionId, String status) {
        String message = VNPAYConstants.TransactionMessage.fromValue(status).getMessage();
        PaymentDetail paymentDetail = paymentDetailRepository.findPaymentDetailByOrderId(orderId).orElseThrow(() -> new PaymentGatewayException("Không tìm thấy orderId: " + orderId));
        paymentDetail.setTransId(transactionId);
        paymentDetail.setMessage(message);
        paymentDetail.setModifiedTime(OffsetDateTime.now());

        paymentDetailRepository.save(paymentDetail);
    }

    public void initTransaction(String orderId, String requestId, String transId, String orderInfo, long amount, PaymentSupplierEnum paymentSupplierEnum) {
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setTransId(transId);
        paymentDetail.setOrderId(orderId);
        paymentDetail.setRequestId(requestId);
        paymentDetail.setAmount(amount);
        paymentDetail.setOrderInfo(orderInfo);
        paymentDetail.setCreatedTime(OffsetDateTime.now());
        paymentDetail.setPaymentSupplier(paymentSupplierEnum);

        paymentDetailRepository.save(paymentDetail);
    }
}
