package vn.edu.fpt.paymentgateway.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentCreateResponse {
    private String paymentUrl;
}
