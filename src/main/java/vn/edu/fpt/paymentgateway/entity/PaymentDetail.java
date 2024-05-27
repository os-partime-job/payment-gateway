package vn.edu.fpt.paymentgateway.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table("PAYMENT_DETAIL")
@Data
public class PaymentDetail {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String
    private Long transId;
    private int amount;
    private String orderInfo;
    private OffsetDateTime createdTime;
    private OffsetDateTime modifiedTime;
}
