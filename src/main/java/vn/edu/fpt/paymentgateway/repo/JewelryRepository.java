package vn.edu.fpt.paymentgateway.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.paymentgateway.entity.Jewelry;

import java.util.List;

@Repository
public interface JewelryRepository extends JpaRepository<Jewelry, Long> {
    List<Jewelry> findAllByIdIn(List<Long> ids);
}