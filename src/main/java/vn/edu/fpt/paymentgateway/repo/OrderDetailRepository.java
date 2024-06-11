package vn.edu.fpt.paymentgateway.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.paymentgateway.entity.OrderDetail;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findAllByCustomerIdOrderByCreatedAtDesc(Long customerId);

    List<OrderDetail> findAllByUniqueOrderId(String orderId);
}