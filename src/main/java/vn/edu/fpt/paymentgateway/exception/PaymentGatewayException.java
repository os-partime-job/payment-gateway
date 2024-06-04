package vn.edu.fpt.paymentgateway.exception;

import lombok.Getter;

@Getter
public class PaymentGatewayException extends RuntimeException {
    private String message;

    public PaymentGatewayException(String message) {
        super(message);
        this.message = message;
    }
}
