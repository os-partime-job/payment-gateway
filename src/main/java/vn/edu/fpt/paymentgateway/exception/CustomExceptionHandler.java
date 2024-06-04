package vn.edu.fpt.paymentgateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.edu.fpt.paymentgateway.payload.BaseResponse;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity.status(500).body(BaseResponse.badReq(exception.getMessage()));
    }

    @ExceptionHandler(value = PaymentGatewayException.class)
    public ResponseEntity<?> handlePaymentGatewayException(PaymentGatewayException exception) {
        return ResponseEntity.status(400).body(BaseResponse.badReq(exception.getMessage()));
    }
}
