package vn.edu.fpt.paymentgateway.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.paymentgateway.entity.Orders;

import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByUniqueOrderId(String uniqueOrderId);
}