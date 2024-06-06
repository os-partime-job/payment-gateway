package vn.edu.fpt.paymentgateway.entity;

import lombok.Data;
import vn.edu.fpt.paymentgateway.constants.PaymentSupplierEnum;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "PAYMENT_DETAIL")
@Data
public class PaymentDetail {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "order_id", unique = true)
    private String orderId;

    @Column(name = "request_id", unique = true)
    private String requestId;

    @Column(name = "trans_id", unique = true)
    private String transId;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "order_info")
    private String orderInfo;

    @Column(name = "status")
    private String status;

    @Column(name = "pay_type")
    private String payType;

    @Column(name = "pay_supplier")
    @Enumerated(EnumType.STRING)
    private PaymentSupplierEnum paymentSupplier;

    @Column(name = "message")
    private String message;

    @Column(name = "created_time")
    private OffsetDateTime createdTime;

    @Column(name = "modified_time")
    private OffsetDateTime modifiedTime;
}
