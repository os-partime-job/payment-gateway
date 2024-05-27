package vn.edu.fpt.paymentgateway.payload;

import lombok.Data;

@Data
public class PaymentCreateRequest {
    private int amount;
    private String orderInfo;
    private String metaData;
}
