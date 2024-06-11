package vn.edu.fpt.paymentgateway.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.edu.fpt.paymentgateway.payload.response.BaseResponse;

@RestControllerAdvice
@Log4j2
public class CustomExceptionHandler {

    @ExceptionHandler(value = {
            RuntimeException.class,
            Exception.class

    })
    public ResponseEntity<?> handleRuntimeException(RuntimeException exception) {
        log.error(exception.getCause(), exception);
        return ResponseEntity.status(500).body(BaseResponse.internalErr(exception.getMessage()));
    }

    @ExceptionHandler(value = PaymentGatewayException.class)
    public ResponseEntity<?> handlePaymentGatewayException(PaymentGatewayException exception) {
        log.error(exception.getCause(), exception);
        return ResponseEntity.status(400).body(BaseResponse.badReq(exception.getMessage()));
    }
}
