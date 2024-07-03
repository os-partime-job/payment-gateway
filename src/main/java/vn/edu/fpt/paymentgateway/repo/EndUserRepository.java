package vn.edu.fpt.paymentgateway.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.paymentgateway.entity.EndUser;

import java.util.Optional;

@Repository
public interface EndUserRepository extends JpaRepository<EndUser, Long> {
    Optional<EndUser> findEndUserByAccountId(Long accountId);

    EndUser findEndUserByPhoneNumber(String phoneNumber);
}