package vn.edu.fpt.paymentgateway.payload.request;

import lombok.*;
import vn.edu.fpt.paymentgateway.third_party.vnpay.contants.VNPAYPaytypeEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VNPayPaymentCreateRequest extends BaseCreatePaymentRequest {
    private VNPAYPaytypeEnum payType;
}
