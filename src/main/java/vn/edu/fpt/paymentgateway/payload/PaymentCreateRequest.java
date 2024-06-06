package vn.edu.fpt.paymentgateway.payload;

import lombok.Data;
import vn.edu.fpt.paymentgateway.payload.request.BaseCreatePaymentRequest;
import vn.edu.fpt.paymentgateway.third_party.vnpay.contants.VNPAYPaytypeEnum;

@Data
public class PaymentCreateRequest extends BaseCreatePaymentRequest {
    private VNPAYPaytypeEnum payType;
}
