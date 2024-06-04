package vn.edu.fpt.paymentgateway.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.paymentgateway.entity.PaymentDetail;

import java.util.Optional;

@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long> {
    Optional<PaymentDetail> findPaymentDetailByTransId(Long aLong);

    Optional<PaymentDetail> findPaymentDetailByOrderId(String orderId);

    boolean existsByOrderId(String orderId);
}