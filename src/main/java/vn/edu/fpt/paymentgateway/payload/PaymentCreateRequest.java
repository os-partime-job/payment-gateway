package vn.edu.fpt.paymentgateway.payload;

import lombok.Data;
import vn.edu.fpt.paymentgateway.third_party.vnpay.contants.VNPAYPaytypeEnum;

@Data
public class PaymentCreateRequest {
    private String orderId;
    private String requestId;
    private long amount;
    private String orderInfo;
    private String metaData;
    private VNPAYPaytypeEnum payType;
}
