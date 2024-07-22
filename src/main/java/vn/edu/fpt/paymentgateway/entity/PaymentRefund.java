package vn.edu.fpt.paymentgateway.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "PAYMENT_REFUND")
@Data
@NoArgsConstructor
public class PaymentRefund {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "order_id", unique = true)
    private String orderId;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "charge_ident")
    private String chargeIdent;

    @Column(name = "status")
    private String status;
}
