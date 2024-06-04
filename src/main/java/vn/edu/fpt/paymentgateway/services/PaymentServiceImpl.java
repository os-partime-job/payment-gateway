package vn.edu.fpt.paymentgateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.fpt.paymentgateway.entity.PaymentDetail;
import vn.edu.fpt.paymentgateway.exception.PaymentGatewayException;
import vn.edu.fpt.paymentgateway.payload.PaymentCheckTransactionResponse;
import vn.edu.fpt.paymentgateway.payload.PaymentCreateRequest;
import vn.edu.fpt.paymentgateway.payload.PaymentCreateResponse;
import vn.edu.fpt.paymentgateway.repo.PaymentDetailRepository;
import vn.edu.fpt.paymentgateway.third_party.vnpay.contants.VNPAYConstants;
import vn.edu.fpt.paymentgateway.third_party.vnpay.contants.VNPAYPaytypeEnum;
import vn.edu.fpt.paymentgateway.third_party.vnpay.service.VNPAYService;

import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private VNPAYService vnpayService;

    @Autowired
    private PaymentDetailRepository paymentDetailRepository;

    @Override
    public PaymentCreateResponse createPayment(HttpServletRequest httpServletRequest,
                                               PaymentCreateRequest request) {
        initTransaction(request.getOrderId(), UUID.randomUUID().toString(), request.getOrderInfo(), request.getAmount(), request.getPayType());
        String paymentUrl = vnpayService.createPaymentUrl(httpServletRequest, request.getOrderId(), request.getOrderInfo(), request.getAmount(), request.getPayType());
        return new PaymentCreateResponse(paymentUrl);
    }

    @Override
    public PaymentCheckTransactionResponse checkPayment(int tid) {
        return null;
    }

    @Override
    public void updatePayment(String orderId, long transactionId, String status) {
        String message = VNPAYConstants.TransactionMessage.fromValue(status).getMessage();
        PaymentDetail paymentDetail = paymentDetailRepository.findPaymentDetailByOrderId(orderId).orElseThrow(() -> new PaymentGatewayException("Không tìm thấy orderId: " + orderId));
        paymentDetail.setTransId(transactionId);
        paymentDetail.setMessage(message);
        paymentDetail.setModifiedTime(OffsetDateTime.now());
        paymentDetailRepository.save(paymentDetail);
    }

    public void initTransaction(String orderId, String requestId, String orderInfo, long amount, VNPAYPaytypeEnum payType) {
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setOrderId(orderId);
        paymentDetail.setRequestId(requestId);
        paymentDetail.setAmount(amount);
        paymentDetail.setOrderInfo(orderInfo);
        paymentDetail.setPayType(payType.name());
        paymentDetail.setCreatedTime(OffsetDateTime.now());
        paymentDetailRepository.save(paymentDetail);
    }
}
