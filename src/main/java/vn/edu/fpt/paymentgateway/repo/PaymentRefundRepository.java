package vn.edu.fpt.paymentgateway.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.paymentgateway.entity.PaymentRefund;

import java.util.Optional;

@Repository
public interface PaymentRefundRepository extends JpaRepository<PaymentRefund, Long> {
    Optional<PaymentRefund> findPaymentRefundByOrderId(String orderId);
}