package vn.edu.fpt.paymentgateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.fpt.paymentgateway.constants.PaymentSupplierEnum;
import vn.edu.fpt.paymentgateway.entity.PaymentDetail;
import vn.edu.fpt.paymentgateway.exception.PaymentGatewayException;
import vn.edu.fpt.paymentgateway.payload.request.BaseCreatePaymentRequest;
import vn.edu.fpt.paymentgateway.payload.request.VNPayPaymentCreateRequest;
import vn.edu.fpt.paymentgateway.payload.response.PaymentCreateResponse;
import vn.edu.fpt.paymentgateway.repo.PaymentDetailRepository;
import vn.edu.fpt.paymentgateway.third_party.vnpay.contants.VNPAYConstants;
import vn.edu.fpt.paymentgateway.third_party.vnpay.contants.VNPAYPaytypeEnum;
import vn.edu.fpt.paymentgateway.third_party.vnpay.service.VNPAYService;

import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;

@Service("paymentVNPayService")
public class PaymentVNPayServiceImpl implements PaymentService {

    @Autowired
    private VNPAYService vnpayService;

    @Autowired
    private PaymentDetailRepository paymentDetailRepository;

    @Override
    public PaymentCreateResponse createPayment(HttpServletRequest httpServletRequest,
                                               BaseCreatePaymentRequest request) {
        VNPayPaymentCreateRequest vnPayPaymentCreateRequest = (VNPayPaymentCreateRequest) request;
        initTransaction(vnPayPaymentCreateRequest.getOrderId(), vnPayPaymentCreateRequest.getRequestId(), vnPayPaymentCreateRequest.getOrderInfo(), vnPayPaymentCreateRequest.getAmount(), PaymentSupplierEnum.STRIPE, vnPayPaymentCreateRequest.getPayType());
        String paymentUrl = vnpayService.createPaymentUrl(httpServletRequest, vnPayPaymentCreateRequest.getOrderId(), vnPayPaymentCreateRequest.getOrderInfo(),
                vnPayPaymentCreateRequest.getAmount(), vnPayPaymentCreateRequest.getPayType());
        return new PaymentCreateResponse(paymentUrl);
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

    public void initTransaction(String orderId, String requestId, String orderInfo, long amount, PaymentSupplierEnum paymentSupplierEnum, VNPAYPaytypeEnum payType) {
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setOrderId(orderId);
        paymentDetail.setRequestId(requestId);
        paymentDetail.setAmount(amount);
        paymentDetail.setOrderInfo(orderInfo);
        paymentDetail.setPayType(payType.name());
        paymentDetail.setCreatedTime(OffsetDateTime.now());
        paymentDetail.setPaymentSupplier(paymentSupplierEnum);
        paymentDetailRepository.save(paymentDetail);
    }
}
