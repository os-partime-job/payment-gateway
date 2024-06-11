package vn.edu.fpt.paymentgateway.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.edu.fpt.paymentgateway.constants.PaymentSupplierEnum;
import vn.edu.fpt.paymentgateway.repo.OrdersRepository;
import vn.edu.fpt.paymentgateway.utils.ApplicationContextHolder;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Component
public class PaymentFactory {

    @Autowired
    private OrdersRepository ordersRepository;

    public static PaymentService getPayment(HttpServletRequest request, PaymentSupplierEnum paymentSupplier) {
        log.info("Get payment for supplier: {} ", paymentSupplier);
        switch (paymentSupplier) {
            case STRIPE:
                return (PaymentService) ApplicationContextHolder.getContext().getBean("paymentStripeService");
            case VNPAY:
                return (PaymentService) ApplicationContextHolder.getContext().getBean("paymentVNPayService");
            default:
                throw new IllegalArgumentException("Khong co payment supplier  " + paymentSupplier);
        }

    }
}
