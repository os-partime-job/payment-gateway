package vn.edu.fpt.paymentgateway.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseCreatePaymentRequest {
    private String orderId;
    private String requestId;
    private long amount;
    private String orderInfo;
    private String metaData;
}
